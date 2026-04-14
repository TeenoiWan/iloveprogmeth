import org.junit.jupiter.api.Test;
import util.encryption.services.textServices.EncryptionService;
import util.encryption.services.textServices.DecryptionService;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptionServiceTest {

    @Test
    void encryptedTextShouldBeDifferent() {
        String input = "HelloWorld";
        String key = "mypassword";

        EncryptionService service = new EncryptionService(input, key);
        service.execute();

        assertNotEquals(input, service.getEncryptedText());
    }

    @Test
    void decryptShouldRestoreOriginal() {
        String input = "HelloWorld";
        String key = "mypassword";

        EncryptionService encrypt = new EncryptionService(input, key);
        encrypt.execute();

        DecryptionService decrypt = new DecryptionService(encrypt.getEncryptedText(), key);
        decrypt.execute();

        assertEquals(input, decrypt.getDecryptedText());
    }

    @Test
    void wrongKeyShouldThrow() {
        String input = "HelloWorld";

        EncryptionService encrypt = new EncryptionService(input, "correctkey");
        encrypt.execute();

        DecryptionService decrypt = new DecryptionService(encrypt.getEncryptedText(), "wrongkey");

        assertThrows(RuntimeException.class, decrypt::execute);
    }

    @Test
    void sameInputShouldProduceDifferentCiphertext() {
        String input = "HelloWorld";
        String key = "mypassword";

        EncryptionService first = new EncryptionService(input, key);
        first.execute();

        EncryptionService second = new EncryptionService(input, key);
        second.execute();

        assertNotEquals(first.getEncryptedText(), second.getEncryptedText());
    }

    @Test
    void emptyInputShouldThrow() {
        EncryptionService service = new EncryptionService("", "mypassword");
        assertDoesNotThrow(service::execute);
    }
}