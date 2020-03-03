package org.indigo.injector.util;

import java.util.Objects;

public class Assert {

    public static <T> void checkNotNull(T object) { checkNotNull(object, "Object can't be null!"); }

    public static <T> void checkNotNull(T object, String msg) {
        if(Objects.isNull(object)) throw new RuntimeException(msg);
    }

}
