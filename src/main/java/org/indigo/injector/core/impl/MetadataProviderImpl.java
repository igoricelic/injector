package org.indigo.injector.core.impl;

import org.indigo.injector.core.DataModel;
import org.indigo.injector.core.MetadataProvider;
import org.indigo.injector.metadata.BeanMetadata;
import org.indigo.injector.metadata.enums.Scope;
import org.indigo.injector.util.ReflectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MetadataProviderImpl implements MetadataProvider {

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
        BeanMetadata metadata = new BeanMetadata();
        metadata.setScope(Scope.SINGLETON);
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
            methodDependencyGrid.put(method, dependencies);
        });
        metadata.setSetterDependencies(methodDependencyGrid);

        List<Field> fieldDependencies = Stream.of(componentClazz.getDeclaredFields())
                .filter(reflectionUtil::isInjectable)
                .collect(Collectors.toList());
        Map<Field, BeanMetadata> fieldDependencyGrid = fieldDependencies.stream()
                .collect(Collectors.toMap(Function.identity(), field -> scan(field.getType())));
        metadata.setFieldDependencies(fieldDependencyGrid);

        return metadata;
    }

}
