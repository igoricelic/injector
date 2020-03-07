package org.indigo.injector.annotations;

import org.indigo.injector.metadata.enums.Scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Injectable {

    Class<?> definition() default Void.class;

    String name() default "";

    Scope scope() default Scope.SINGLETON;

}
