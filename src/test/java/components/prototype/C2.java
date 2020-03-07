package components.prototype;

import org.indigo.injector.annotations.Inject;

import java.util.UUID;

public class C2 {

    private final C3 c3;

    private final String code;

    @Inject
    public C2(C3 c3) {
        this.c3 = c3;
        this.code = UUID.randomUUID().toString();
    }

    public C3 getC3() {
        return c3;
    }

    public String getCode() {
        return code;
    }

}
