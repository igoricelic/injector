package components;

import org.indigo.injector.annotations.Inject;

public class C4 {

    private final C2 c2;

    @Inject
    public C4(C2 c2) {
        this.c2 = c2;
    }
}
