package util.encryption.services.textServices;

import logic.superClass.Tool;
import util.encryption.services.CipherCore;
import util.encryption.services.KeyService;

import javax.crypto.SecretKey;
import java.util.Base64;

public class EncryptionService extends Tool {


    private final KeyService keyService = new KeyService();
    private final CipherCore cipherCore = new CipherCore();
    private String text;
    private String password;
    private String encryptedText;

    public EncryptionService(String text,String password){
        this.text = text;
        this.password = password;
    }

    @Override
    public void execute() {
        try {
            SecretKey key = keyService.generateKey(password);

            byte[] encrypted = cipherCore.encrypt(text.getBytes(),key);

            encryptedText = Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    public String getEncryptedText(){
        return encryptedText;
    }
}
