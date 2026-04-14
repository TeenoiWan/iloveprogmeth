package util.encryption.services.textServices;

import logic.superClass.Tool;
import util.encryption.services.CipherCore;
import util.encryption.services.KeyService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DecryptionService extends Tool {

    private final KeyService keyService = new KeyService();
    private final CipherCore cipherCore  = new CipherCore();
    private String text;
    private String password;
    private String decryptedText;

    public DecryptionService(String text,String password){
        this.text = text;
        this.password = password;
    }

    @Override
    public void execute() {
        try {
            byte[] combined = Base64.getDecoder().decode(text);

            SecretKey key = keyService.generateKey(password);

            byte[] decrypted = cipherCore.decrypt(combined,key);

            decryptedText = new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Decryption failed",e);
        }
    }

    public String getDecryptedText(){
        return decryptedText;
    }

}
