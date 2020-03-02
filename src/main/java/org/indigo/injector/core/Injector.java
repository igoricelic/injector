package org.indigo.injector.core;

public interface Injector {

    <T> T getInstance(Class<T> clazz);

}
