package components.singleton;

import org.indigo.injector.annotations.Inject;

public class C3 {

    private final C4 c4;

    @Inject
    public C3(C4 c4) {
        this.c4 = c4;
    }

}
