package org.indigo.injector.annotations;

public @interface Scope {

    org.indigo.injector.metadata.enums.Scope value() default org.indigo.injector.metadata.enums.Scope.SINGLETON;

}
