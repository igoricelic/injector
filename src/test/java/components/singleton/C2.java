package components.singleton;

import org.indigo.injector.annotations.Inject;

public class C2 {

    private final C3 c3;

    @Inject
    public C2(C3 c3) {
        this.c3 = c3;
    }
}
