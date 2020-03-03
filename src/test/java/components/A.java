package components;

import org.indigo.injector.annotations.Inject;

public class A {

    private final B b;

    private final C c;

    @Inject
    public A(B b, C c) {
        this.b = b;
        this.c = c;
    }

    public B getB() {
        return b;
    }

    public C getC() {
        return c;
    }
}
