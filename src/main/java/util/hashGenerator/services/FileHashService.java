package util.hashGenerator.services;


import logic.superClass.Tool;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class FileHashService extends Tool {

    private String inputPath;
    private String algorithm;
    private String hashedText;

    public FileHashService(String inputPath, String algorithm) {
        this.inputPath = inputPath;
        this.algorithm = algorithm;
    }


    @Override
    public void execute(){
        try(FileInputStream fileInputStream = new FileInputStream(inputPath);){

            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while((bytesRead = fileInputStream.read(buffer)) != -1) { digest.update(buffer, 0, bytesRead); }

            byte[] hashedBytes = digest.digest();
            hashedText = BytesToHex.bytesToHex(hashedBytes);


        } catch (Exception e) {
            throw new RuntimeException("File hash failed" + algorithm,e);
        }
    }


    public String getHashedText(){
        return hashedText;
    }
}
