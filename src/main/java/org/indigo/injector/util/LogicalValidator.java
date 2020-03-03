package org.indigo.injector.util;

import org.indigo.injector.metadata.BeanMetadata;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class LogicalValidator {

    private final Set<BeanMetadata> validatedBeans;

    public LogicalValidator() {
        validatedBeans = new CopyOnWriteArraySet<>();
    }

    public void validateCyclical(BeanMetadata beanMetadata) {
        if(!validatedBeans.contains(beanMetadata)) {
            if(isCyclic(beanMetadata, new HashSet<>(), new HashSet<>()))
                throw new RuntimeException(String.format("Circular import %s!", beanMetadata.getType().getName()));
            validatedBeans.add(beanMetadata);
        }
    }

    private boolean isCyclic(BeanMetadata beanMetadata, Set<BeanMetadata> visited, Set<BeanMetadata> recStack) {

        if(recStack.contains(beanMetadata)) return true;
        if(visited.contains(beanMetadata)) return false;

        visited.add(beanMetadata);
        recStack.add(beanMetadata);
        for (BeanMetadata dependency : beanMetadata.getConstructorDependencies()) {
            if(isCyclic(dependency, visited, recStack)) return true;
        }
        recStack.remove(beanMetadata);

        return false;
    }

}
