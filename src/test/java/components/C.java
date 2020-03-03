package components;

import org.indigo.injector.annotations.Inject;

public class C {

    private B b;

    @Inject
    public void setB(B b) {
        this.b = b;
    }

    public B getB() {
        return b;
    }
}
