import components.A;
import components.C1;
import org.indigo.injector.core.DataModel;
import org.indigo.injector.core.MetadataProvider;
import org.indigo.injector.core.impl.MetadataProviderImpl;
import org.indigo.injector.util.LogicalValidator;
import org.indigo.injector.util.ReflectionUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

public class MetadataProviderTests {

    private LogicalValidator logicalUtil;

    private MetadataProvider metadataProvider;

    public MetadataProviderTests() {
        logicalUtil = new LogicalValidator();
        metadataProvider = new MetadataProviderImpl(DataModel.instance(), new ReflectionUtil());
    }

    @Test
    void scanMetadataTest() {
//        assertFalse(logicalUtil.isCyclic(metadataProvider.scan(A.class, new HashSet<>())));
//        assertTrue(logicalUtil.isCyclic(metadataProvider.scan(C1.class, new HashSet<>())));
//        assertDoesNotThrow(logicalUtil.validateCyclical(metadataProvider.scan(A.class)));
//        assertThrows(logicalUtil.validateCyclical(metadataProvider.scan(C1.class)));
    }

}
