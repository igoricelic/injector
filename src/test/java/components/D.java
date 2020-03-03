package components;

import org.indigo.injector.annotations.Inject;

public class D {

    private A a;

    @Inject
    private B b;

    @Inject
    private C c;

    @Inject
    public void setA(A a) {
        this.a = a;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public C getC() {
        return c;
    }

}
