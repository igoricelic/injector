package org.indigo.injector.util;

import org.indigo.injector.annotations.Inject;

import java.lang.reflect.*;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class ReflectionUtil {

    public Optional<Constructor<?>> getDefaultConstructor(Class<?> clazz) {
        try {
            return Optional.ofNullable(clazz.getDeclaredConstructor());
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        }
    }

    public Optional<Constructor<?>> getInjectableConstructor(Class<?> clazz) {
        return Stream.of(clazz.getDeclaredConstructors())
                .filter(cons -> Objects.nonNull(cons.getAnnotation(Inject.class)))
                .reduce((x, y) -> { throw new RuntimeException("You must have one and only one injectable constructor!"); });
    }

    public boolean isInjectable(AccessibleObject object) {
        return Objects.nonNull(object.getAnnotation(Inject.class));
    }

    public void invokeMethod(Object target, Method method, Object... args) {
        try {
            method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setValue(Object target, Object value, Field field) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void makeAffordable(AccessibleObject accessibleObject) {
        Assert.checkNotNull(accessibleObject);
        if(Modifier.isFinal(readModifiers(accessibleObject))) {
            String throwableMessage = "Final %s element '%s'. Final elements can't be mapped!";
            if(accessibleObject instanceof Field)
                throwableMessage = String.format(throwableMessage, "field", ((Field) accessibleObject).getName());
            else if(accessibleObject instanceof Method)
                throwableMessage = String.format(throwableMessage, "method", ((Method) accessibleObject).getName());
            throw new RuntimeException(throwableMessage);
        }
        accessibleObject.setAccessible(true);
    }

    public int readModifiers(AccessibleObject accessibleObject) {
        Assert.checkNotNull(accessibleObject);
        if (accessibleObject instanceof Field) {
            return ((Field) accessibleObject).getModifiers();
        } else if(accessibleObject instanceof Method) {
            return ((Method) accessibleObject).getModifiers();
        }
        throw new RuntimeException("Modifiers of present object can't be read!");
    }


}
