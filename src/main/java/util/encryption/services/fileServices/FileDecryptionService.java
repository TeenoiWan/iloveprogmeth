package util.encryption.services.fileServices;

import logic.superClass.Tool;
import util.encryption.services.CipherCore;
import util.encryption.services.KeyService;

import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDecryptionService extends Tool {

    private final KeyService keyService = new KeyService();
    private final CipherCore cipherCore = new CipherCore();
    private String inputPath;
    private String outputPath;
    private String password;

    public FileDecryptionService(String inputPath,String outputPath,String password) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.password = password;
    }

    @Override
    public void execute() {
        try{
            byte[] inputData = Files.readAllBytes(Path.of(inputPath));

            SecretKey key = keyService.generateKey(password);

            byte[] outputData = cipherCore.decrypt(inputData,key);

            Files.write(Path.of(outputPath),outputData);

        }catch (Exception e){
            throw new RuntimeException("File decryption failed",e);
        }

    }
}
