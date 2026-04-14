import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import util.encryption.services.KeyService;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

public class KeyServiceTest {

    @Test
    @DisplayName("Generated key should not be null")
    void keyShouldNotBeNull() {
        KeyService service = new KeyService();
        SecretKey key = service.generateKey("testpassword");
        assertNotNull(key);
    }

    @Test
    @DisplayName("Generated key algorithm should be AES")
    void keyShouldBeAES() {
        KeyService service = new KeyService();
        SecretKey key = service.generateKey("testpassword");
        assertEquals("AES", key.getAlgorithm());
    }

    @Test
    @DisplayName("Same password should always produce same key")
    void samePasswordShouldProduceSameKey() {
        KeyService service = new KeyService();
        SecretKey key1 = service.generateKey("testpassword");
        SecretKey key2 = service.generateKey("testpassword");
        assertArrayEquals(key1.getEncoded(), key2.getEncoded());
    }

    @Test
    @DisplayName("Different passwords should produce different keys")
    void differentPasswordsShouldProduceDifferentKeys() {
        KeyService service = new KeyService();
        SecretKey key1 = service.generateKey("password1");
        SecretKey key2 = service.generateKey("password2");
        assertFalse(java.util.Arrays.equals(key1.getEncoded(), key2.getEncoded()));
    }
}