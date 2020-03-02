package org.indigo.injector.core;

import org.indigo.injector.metadata.BeanMetadata;

import java.util.Set;

public interface MetadataProvider {

    BeanMetadata scan(Class<?> clazz, Set<Class<?>> road);

}
