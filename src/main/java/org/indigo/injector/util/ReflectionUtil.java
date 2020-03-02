package org.indigo.injector.util;

import org.indigo.injector.annotations.Inject;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
//        Constructor<?> constructor = null;
//        int constructorCounter = 0;
//        for(Constructor cons: clazz.getConstructors()) {
//            if(Objects.nonNull(cons.getAnnotation(Inject.class))) {
//                constructor = cons;
//                constructorCounter++;
//                if(constructorCounter > 1) {
//                    // todo: exception
//                    throw new RuntimeException("You must have one and only one injectable constructor!");
//                }
//            }
//        }
//        return Optional.ofNullable(constructor);
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

}
