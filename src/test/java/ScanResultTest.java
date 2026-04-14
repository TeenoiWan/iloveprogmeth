import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import util.portScanner.dataType.ScanResult;

import static org.junit.jupiter.api.Assertions.*;

public class ScanResultTest {

    @Test
    @DisplayName("Constructor should store port and status correctly")
    void constructorShouldStoreValues() {
        ScanResult result = new ScanResult(80, "OPEN");
        assertEquals(80, result.getPort());
        assertEquals("OPEN", result.getStatus());
    }

    @Test
    @DisplayName("Default constructor should have port 0 and empty status")
    void defaultConstructorShouldHaveDefaults() {
        ScanResult result = new ScanResult();
        assertEquals(0, result.getPort());
        assertEquals("", result.getStatus());
    }
}