package org.indigo.injector.core;

import org.indigo.injector.metadata.BeanMetadata;

public interface MetadataProvider {

    BeanMetadata scan(Class<?> clazz);

}
