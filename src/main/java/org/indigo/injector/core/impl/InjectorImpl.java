package org.indigo.injector.core.impl;

import org.indigo.injector.core.MetadataProvider;
import org.indigo.injector.util.ReflectionUtil;

public class InjectorImpl extends AbstractInjector {

    public InjectorImpl(ReflectionUtil reflectionUtil, MetadataProvider metadataProvider) {
        super(reflectionUtil, metadataProvider);
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        return (T) getInstance(readMetadata(clazz));
    }
}
