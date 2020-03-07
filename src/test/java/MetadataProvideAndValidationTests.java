import components.singleton.*;
import org.indigo.injector.core.DataModel;
import org.indigo.injector.core.MetadataProvider;
import org.indigo.injector.core.impl.MetadataProviderImpl;
import org.indigo.injector.metadata.BeanMetadata;
import org.indigo.injector.util.LogicalValidator;
import org.indigo.injector.util.ReflectionUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MetadataProvideAndValidationTests {

    private LogicalValidator validator;

    private MetadataProvider metadataProvider;

    public MetadataProvideAndValidationTests() {
        validator = new LogicalValidator();
        metadataProvider = new MetadataProviderImpl(
                DataModel.instance().bind(Component.class, C1.class), new ReflectionUtil());
    }

    @Test
    void scanMetadataTest() {
        BeanMetadata metadataA = metadataProvider.scan(A.class);
        BeanMetadata metadataC = metadataProvider.scan(C.class);
        BeanMetadata metadataD = metadataProvider.scan(D.class);

        assertFalse(metadataA.getConstructorDependencies().isEmpty());
        assertTrue(metadataC.getConstructorDependencies().isEmpty());
        assertTrue(metadataD.getConstructorDependencies().isEmpty());

        assertTrue(metadataA.getSetterDependencies().isEmpty());
        assertFalse(metadataC.getSetterDependencies().isEmpty());
        assertFalse(metadataD.getSetterDependencies().isEmpty());

        assertTrue(metadataA.getFieldDependencies().isEmpty());
        assertTrue(metadataC.getFieldDependencies().isEmpty());
        assertFalse(metadataD.getFieldDependencies().isEmpty());
    }

    @Test
    void validateTest() {
        assertDoesNotThrow(() -> validator.validateCyclical(metadataProvider.scan(A.class)));
        assertDoesNotThrow(() -> validator.validateCyclical(metadataProvider.scan(D.class)));
        assertThrows(RuntimeException.class, () -> validator.validateCyclical(metadataProvider.scan(C1.class)));
    }

}
