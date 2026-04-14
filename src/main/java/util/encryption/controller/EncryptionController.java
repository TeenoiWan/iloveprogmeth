package util.encryption.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.interfaces.Resettable;
import logic.superClass.BaseController;
import logic.superClass.InputModeSwitcher;

import javafx.scene.control.*;
import util.encryption.services.fileServices.FileDecryptionService;
import util.encryption.services.fileServices.FileEncryptionService;
import util.encryption.services.textServices.DecryptionService;
import util.encryption.services.textServices.EncryptionService;

public class EncryptionController extends BaseController implements Resettable {

    //Input Fields
    @FXML private TextArea encryptInput;
    @FXML private TextField encryptFilePath;
    @FXML private PasswordField encryptKey;

    @FXML private TextArea decryptInput;
    @FXML private TextField decryptFilePath;
    @FXML private PasswordField decryptKey;

    //Output text
    @FXML private TextArea encryptOutput;
    @FXML private TextArea decryptOutput;

    //Radio button
    @FXML private ToggleGroup encryptModeGroup;
    @FXML private RadioButton encryptTextModeBtn;
    @FXML private RadioButton encryptFileModeBtn;
    @FXML private ToggleGroup decryptModeGroup;
    @FXML private RadioButton decryptTextModeBtn;
    @FXML private RadioButton decryptFileModeBtn;



    /// ////////////////////////////////////////////////////////////////
    //Encrypt
    @FXML public void handleEncrypt(){
        try{
            String key = encryptKey.getText().trim();

            if (key.isEmpty()) {
                showError("Invalid Key", "Please enter your key");
                return;
            }


            if(encryptTextModeBtn.isSelected()){
                String input = encryptInput.getText().trim();

                if(input.isEmpty()){
                    showError("Invalid Text","Please enter your text");
                    return;
                }

                encryptOutput.clear();

                EncryptionService encrypt = new EncryptionService(input,key);
                encrypt.execute();
                encryptOutput.setText(encrypt.getEncryptedText());
                return;
            }

            if(encryptFileModeBtn.isSelected()){
                String filePath = encryptFilePath.getText().trim();

                if (filePath.isEmpty()){
                    showError("Invalid File","Please select a file");
                    return;
                }

                String outputPath = filePath + ".enc";
                FileEncryptionService service = new FileEncryptionService(filePath,outputPath,key);

                service.execute();

                showSuccess("File encrypted:\n"+outputPath);
            }



        } catch (Exception e){
            showError("Encryption Error",e.getMessage());
        }
    }



    //Decrypt
    @FXML public void handleDecrypt(){
        try{
            String key = decryptKey.getText().trim();
            if(key.isEmpty()){
                showError("Invalid Key", "Please enter your key");
                return;
            }


            if(decryptTextModeBtn.isSelected()){
                String input = decryptInput.getText().trim();

                if(input.isEmpty()){
                    showError("Invalid Text","Please enter your text");
                    return;
                }

                decryptOutput.clear();

                DecryptionService decrypt = new DecryptionService(input,key);
                decrypt.execute();
                decryptOutput.setText(decrypt.getDecryptedText());
                return;
            }

            if(decryptFileModeBtn.isSelected()){
                String filePath = decryptFilePath.getText().trim();

                if(filePath.isEmpty()){
                    showError("Invalid File","Please select a file");
                    return;
                }
                String outputPath;
                if (filePath.endsWith(".enc")) {
                    String original = filePath.replaceAll("\\.enc$", "");
                    String ext = original.substring(original.lastIndexOf("."));
                    String nameWithoutExt = original.substring(0, original.lastIndexOf("."));
                    outputPath = nameWithoutExt + ".decrypted" + ext;
                } else {
                    outputPath = filePath + ".decrypted";
                }
                FileDecryptionService service = new FileDecryptionService(filePath,outputPath,key);

                service.execute();

                showSuccess("File decrypted:\n"+outputPath);
            }

        } catch (Exception e){
            showError("Decryption Error",e.getMessage());
        }
    }

/// ///////////////////////////////////////////////////////////////////////////




    //Change input mode
    @FXML private StackPane encryptInputStack;
    @FXML private VBox encryptTextPane;
    @FXML private VBox encryptFilePane;
    @FXML private StackPane decryptInputStack;
    @FXML private VBox decryptTextPane;
    @FXML private VBox decryptFilePane;
    private InputModeSwitcher encryptController ;
    private InputModeSwitcher decryptController;



    @FXML
    public void initialize() {
        encryptController = new InputModeSwitcher(encryptInputStack,encryptTextPane,encryptFilePane);
        decryptController = new InputModeSwitcher(decryptInputStack,decryptTextPane,decryptFilePane);
        encryptController.switchToTextMode();
        decryptController.switchToTextMode();

        //encrypt
        encryptModeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == encryptTextModeBtn) {
                encryptController.switchToTextMode();
            } else if (newVal == encryptFileModeBtn) {
                encryptController.switchToFileMode();
            }
        });

        //decrypt
        decryptModeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == decryptTextModeBtn) {
                decryptController.switchToTextMode();
            } else if (newVal == decryptFileModeBtn) {
                decryptController.switchToFileMode();
            }
        });

    }

    //Browse
    @FXML public void handleEncryptSelectFile(){
        encryptFilePath.setText(selectFile());
    }
    @FXML public void handleDecryptSelectFile(){
        decryptFilePath.setText(selectFile());
    }

    //reset btn
    @Override
    public void reset(){
        resetDecrypt();
        resetEncrypt();
    }

    private void resetEncrypt() {
        encryptInput.clear();
        encryptFilePath.clear();
        encryptKey.clear();
        encryptOutput.clear();
        encryptTextModeBtn.setSelected(true);
        encryptController.switchToTextMode();
    }

    private void resetDecrypt() {
        decryptInput.clear();
        decryptFilePath.clear();
        decryptKey.clear();
        decryptOutput.clear();
        decryptTextModeBtn.setSelected(true);
        decryptController.switchToTextMode();
    }
    @FXML
    public void handleEncryptReset(){
        resetEncrypt();
    }
    @FXML
    public void handleDecryptReset() {resetDecrypt();}
}
