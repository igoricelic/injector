package org.indigo.injector.core;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DataModel {

    private final Map<Class<?>, Class<?>> state;

    private DataModel() {
        state = new ConcurrentHashMap<>();
    }

    public static DataModel instance() {
        return new DataModel();
    }

    public DataModel bind(Class<?> typeFrom, Class<?> typeTo) {
        state.put(typeFrom, typeTo);
        return this;
    }

    public Optional<Class<?>> bindableType(Class<?> baseType) {
        return Optional.ofNullable(state.get(baseType));
    }

}
