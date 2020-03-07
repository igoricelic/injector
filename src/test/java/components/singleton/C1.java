package components.singleton;

import org.indigo.injector.annotations.Inject;

public class C1 implements Component {

    private final C2 c2;

    @Inject
    public C1(C2 c2) {
        this.c2 = c2;
    }
}
