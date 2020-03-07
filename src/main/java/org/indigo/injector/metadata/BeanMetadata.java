package org.indigo.injector.metadata;

import lombok.Data;
import lombok.ToString;
import org.indigo.injector.metadata.enums.Scope;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Data
public class BeanMetadata {

    @ToString.Include
    private String name;

    @ToString.Include
    private Scope scope = Scope.SINGLETON;

    @ToString.Include
    private Class<?> type;

    private Constructor<?> initializer;

    private LinkedList<BeanMetadata> constructorDependencies = new LinkedList<>();

    private Map<Method, LinkedList<BeanMetadata>> setterDependencies = new HashMap<>();

    private Map<Field, BeanMetadata> fieldDependencies = new HashMap<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeanMetadata that = (BeanMetadata) o;
        return scope == that.scope &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scope, type);
    }

}
