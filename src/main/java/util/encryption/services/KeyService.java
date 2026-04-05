package util.encryption.services;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;


public class KeyService {

    public SecretKey generateKey(String key){
        try{
            byte[] keyBytes = key.getBytes();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(keyBytes);

            byte[] aesKeys = Arrays.copyOf(hash,16);

            return new SecretKeySpec(aesKeys,"AES");
        } catch (Exception e) {
            throw new RuntimeException("Error generating key",e);
        }
    }
}
