package util.hashGenerator.services;


import logic.superClass.Tool;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


public class TextHashService extends Tool {

    private String algorithm;
    private String text;
    private String hashedText;

    public TextHashService(String text, String algorithm){
        this.text = text;
        this.algorithm = algorithm;
    }

    @Override
    public void execute() {
        try{
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashByte = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            hashedText = BytesToHex.bytesToHex(hashByte);
            }catch (Exception e){
            throw new RuntimeException("Hashing failed: " + algorithm ,e);
        }
    }


    public String getHashedText(){
        return hashedText;
    }
}
