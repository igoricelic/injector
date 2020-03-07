package org.indigo.injector.core.impl;

import org.indigo.injector.core.MetadataProvider;
import org.indigo.injector.util.LogicalValidator;
import org.indigo.injector.util.ReflectionUtil;

public final class InjectorImpl extends AbstractInjector {

    public InjectorImpl(ReflectionUtil reflectionUtil, MetadataProvider metadataProvider, LogicalValidator validator) {
        super(validator, reflectionUtil, metadataProvider);
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        return (T) getInstance(readMetadata(clazz));
    }
}
