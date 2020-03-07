import components.prototype.C1;
import components.prototype.C2;
import org.indigo.injector.core.Injector;
import org.indigo.injector.core.InjectorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InjectPrototypeTests {

    private Injector injector;

    public InjectPrototypeTests() {
        injector = InjectorFactory.getInjector();
    }

    @Test
    void getPrototypeTest() {
        String code1 = injector.getInstance(C1.class).getCode();
        String code2 = injector.getInstance(C1.class).getCode();
        String code3 = injector.getInstance(C1.class).getCode();

        assertNotEquals(code1, code2);
        assertNotEquals(code1, code3);
        assertNotEquals(code2, code3);
    }

    @Test
    void prototypeSingletonTest() {
        C1 c11 = injector.getInstance(C1.class);
        C1 c12 = injector.getInstance(C1.class);

        C2 c21 = injector.getInstance(C2.class);
        C2 c22 = injector.getInstance(C2.class);

        assertNotEquals(c11.getCode(), c12.getCode());
        assertEquals(c21.getCode(), c22.getCode());
        assertEquals(c11.getC3().getCode(), c22.getC3().getCode());
        assertEquals(c12.getC3().getCode(), c21.getC3().getCode());
        assertNotEquals(c11.getCode(), c22.getCode());
    }

}
