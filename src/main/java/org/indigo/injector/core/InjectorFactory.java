package org.indigo.injector.core;

import org.indigo.injector.core.impl.InjectorImpl;
import org.indigo.injector.core.impl.MetadataProviderImpl;
import org.indigo.injector.util.LogicalValidator;
import org.indigo.injector.util.ReflectionUtil;

public class InjectorFactory {

    public static Injector getInjector() {
        return getInjector(DataModel.instance());
    }

    public static Injector getInjector(DataModel model) {
        ReflectionUtil reflectionUtil = new ReflectionUtil();
        return new InjectorImpl(reflectionUtil, new MetadataProviderImpl(model, reflectionUtil), new LogicalValidator());
    }

}
