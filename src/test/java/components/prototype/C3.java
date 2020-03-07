package components.prototype;

import java.util.UUID;

public class C3 {

    private final String code;

    public C3() {
        code = UUID.randomUUID().toString();
    }

    public String getCode() {
        return code;
    }

}
