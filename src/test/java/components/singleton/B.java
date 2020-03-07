package components.singleton;

import org.indigo.injector.annotations.Inject;

public class B {

    private C c;

    @Inject
    public void setC(C c) {
        this.c = c;
    }

    public C getC() {
        return c;
    }
}
