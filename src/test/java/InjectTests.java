import components.*;
import org.indigo.injector.core.Injector;
import org.indigo.injector.core.InjectorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InjectTests {

    private Injector injector;

    public InjectTests() {
        injector = InjectorFactory.getInjector();
    }

    @Test
    void basicInjectTest() {
        A a = injector.getInstance(A.class);
        B b = injector.getInstance(B.class);
        C c = injector.getInstance(C.class);
        D d = injector.getInstance(D.class);
        assertThrows(RuntimeException.class, () -> injector.getInstance(C1.class));

        assertNotNull(a);
        assertNotNull(a.getB());
        assertNotNull(a.getC());

        assertNotNull(b);
        assertNotNull(b.getC());
        assertNotNull(c);
        assertNotNull(c.getB());

        assertNotNull(d);
        assertNotNull(d.getA());
        assertNotNull(d.getB());
        assertNotNull(d.getC());

        assertEquals(System.identityHashCode(a.getB()), System.identityHashCode(b));
        assertEquals(System.identityHashCode(a.getC()), System.identityHashCode(c));
    }

}
