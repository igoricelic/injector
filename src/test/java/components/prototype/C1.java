package components.prototype;

import org.indigo.injector.annotations.Inject;
import org.indigo.injector.annotations.Injectable;
import org.indigo.injector.metadata.enums.Scope;

import java.util.UUID;

@Injectable(scope = Scope.PROTOTYPE)
public class C1 {

    private final C3 c3;

    private final String code;

    @Inject
    public C1(C3 c3) {
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
