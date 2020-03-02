package org.indigo.injector.core.impl;

import org.indigo.injector.core.Injector;
import org.indigo.injector.core.MetadataProvider;
import org.indigo.injector.metadata.BeanMetadata;
import org.indigo.injector.metadata.enums.Scope;
import org.indigo.injector.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

abstract class AbstractInjector implements Injector {

    private final ReflectionUtil reflectionUtil;

    private final MetadataProvider metadataProvider;

    private final Map<BeanMetadata, Object> state;

    AbstractInjector(ReflectionUtil reflectionUtil, MetadataProvider metadataProvider) {
        this.reflectionUtil = reflectionUtil;
        this.metadataProvider = metadataProvider;
        this.state = new ConcurrentHashMap<>();
    }

    protected Object getInstance(BeanMetadata beanMetadata) {
        if(Scope.SINGLETON.equals(beanMetadata.getScope()) && state.containsKey(beanMetadata))
            return state.get(beanMetadata);
        var dependencies = beanMetadata.getConstructorDependencies()
                .stream().map(this::getInstance).toArray();
        try {
            Object instance = beanMetadata.getInitializer().newInstance(dependencies);
            state.put(beanMetadata, instance);
            beanMetadata.getSetterDependencies()
                    .forEach((setter, parameters) -> reflectionUtil.invokeMethod(instance, setter, parameters.stream().map(this::getInstance).toArray()));

            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected BeanMetadata readMetadata(Class<?> clazz) {
        return metadataProvider.scan(clazz, new HashSet<>());
    }

}
