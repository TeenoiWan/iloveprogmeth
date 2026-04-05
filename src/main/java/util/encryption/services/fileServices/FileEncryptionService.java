package util.encryption.services.fileServices;

import logic.superClass.Tool;
import util.encryption.services.CipherCore;
import util.encryption.services.KeyService;

import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileEncryptionService extends Tool {

    private final KeyService keyService = new KeyService();
    private final CipherCore cipherCore = new CipherCore();
    private String inputPath;
    private String password;
    private String outputPath;

    public FileEncryptionService(String inputPath,String outputPath,String password){
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.password = password;
    }

    public void execute(){
        try {
            System.out.println(inputPath);
            byte[] inputData = Files.readAllBytes(Path.of(inputPath));

            SecretKey key = keyService.generateKey(password);

            byte[] outputData = cipherCore.encrypt(inputData,key);

            Files.write(Path.of(outputPath), outputData);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("File encryption failed", e);
        }
    }
}
