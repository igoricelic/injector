package org.indigo.injector.core.impl;

import org.indigo.injector.annotations.Injectable;
import org.indigo.injector.core.DataModel;
import org.indigo.injector.core.MetadataProvider;
import org.indigo.injector.metadata.BeanMetadata;
import org.indigo.injector.metadata.enums.Scope;
import org.indigo.injector.util.ReflectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MetadataProviderImpl implements MetadataProvider {

    private static final String EMPTY_WORD = "";

    private final DataModel dataModel;

    private final ReflectionUtil reflectionUtil;

    private final Map<Class<?>, BeanMetadata> state;

    public MetadataProviderImpl(DataModel dataModel, ReflectionUtil reflectionUtil) {
        this.dataModel = dataModel;
        this.reflectionUtil = reflectionUtil;
        state = new ConcurrentHashMap<>();
    }

    @Override
    public BeanMetadata scan(Class<?> clazz) {
        final Class<?> componentClazz = dataModel.bindableType(clazz).orElse(clazz);
        if(state.containsKey(componentClazz)) return state.get(componentClazz);

        Injectable injectableAnnotation = componentClazz.getAnnotation(Injectable.class);
        BeanMetadata metadata = new BeanMetadata();
        if (Objects.nonNull(injectableAnnotation)) {
            metadata.setScope(injectableAnnotation.scope());
            metadata.setName(EMPTY_WORD.equals(injectableAnnotation.name()) ? componentClazz.getName() : injectableAnnotation.name());
        } else {
            metadata.setScope(Scope.SINGLETON);
            metadata.setName(componentClazz.getName());
        }
        metadata.setType(componentClazz);

        state.put(componentClazz, metadata);

        Constructor constructor = reflectionUtil.getDefaultConstructor(componentClazz)
                .or(() -> reflectionUtil.getInjectableConstructor(componentClazz))
                .orElseThrow(() -> new RuntimeException("Not present constructor!"));
        metadata.setInitializer(constructor);

        LinkedList<BeanMetadata> constructorDependencies = new LinkedList<>();
        for(Parameter parameter: constructor.getParameters()) {
            constructorDependencies.addLast(scan(parameter.getType()));
        }
        metadata.setConstructorDependencies(constructorDependencies);

        List<Method> methodDependencies = Stream.of(componentClazz.getDeclaredMethods())
                .filter(reflectionUtil::isInjectable)
                .collect(Collectors.toList());
        Map<Method, LinkedList<BeanMetadata>> methodDependencyGrid = new HashMap<>();
        methodDependencies.forEach(method -> {
            LinkedList<BeanMetadata> dependencies = new LinkedList<>();
            for(Parameter parameter: method.getParameters()) {
                dependencies.addLast(scan(parameter.getType()));
            }
            reflectionUtil.makeAffordable(method);
            methodDependencyGrid.put(method, dependencies);
        });
        metadata.setSetterDependencies(methodDependencyGrid);

        List<Field> fieldDependencies = Stream.of(componentClazz.getDeclaredFields())
                .filter(reflectionUtil::isInjectable)
                .collect(Collectors.toList());
        fieldDependencies.forEach(reflectionUtil::makeAffordable);
        Map<Field, BeanMetadata> fieldDependencyGrid = fieldDependencies.stream()
                .collect(Collectors.toMap(Function.identity(), field -> scan(field.getType())));
        metadata.setFieldDependencies(fieldDependencyGrid);

        return metadata;
    }

}
