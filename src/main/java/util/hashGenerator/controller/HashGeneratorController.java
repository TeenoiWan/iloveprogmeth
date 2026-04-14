package util.hashGenerator.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.interfaces.Resettable;
import logic.superClass.InputModeSwitcher;

import javafx.fxml.FXML;
import logic.superClass.BaseController;
import javafx.scene.control.*;
import util.hashGenerator.dataType.ActionType;
import util.hashGenerator.services.FileHashService;
import util.hashGenerator.services.TextHashService;

public class HashGeneratorController extends BaseController implements Resettable {

    // input
@FXML private TextArea hashInput;
@FXML private TextField hashFilePath;

//Radio
    @FXML private ToggleGroup hashModeGroup;
    @FXML private RadioButton hashTextModeBtn;
    @FXML private RadioButton hashFileModeBtn;

//CheckBox
    @FXML private CheckBox md5Check;
    @FXML private CheckBox sha1Check;
    @FXML private CheckBox sha256Check;
    @FXML private CheckBox sha512Check;

//Output Pane
    @FXML private VBox hashOutputArea;

/// ///////////////////////////////////////////////////////////////////
    @FXML private void handleHash(){

    if(!md5Check.isSelected() &&
        !sha1Check.isSelected() &&
        !sha256Check.isSelected() &&
        !sha512Check.isSelected()) {
        showError("No Algorithm Selected","Please select at least one hash algorithm");
        return;
    }

    //hash text
    if(hashTextModeBtn.isSelected()){
        String input = hashInput.getText().trim();
        ActionType actionType = ActionType.COPY;

        if(input.isEmpty()){
            showError("Invalid Input","Please enter your text");
            return;
        }

        hashOutputArea.getChildren().clear();

        if(md5Check.isSelected()){
            hashTextAsync(input,"MD5",actionType);
        }
        if(sha1Check.isSelected()){
            hashTextAsync(input,"SHA-1",actionType);
        }
        if(sha256Check.isSelected()){
            hashTextAsync(input,"SHA-256",actionType);
        }
        if(sha512Check.isSelected()){
            hashTextAsync(input,"SHA-512",actionType);
        }

    }

    //hash file
    if(hashFileModeBtn.isSelected()){
        String path = hashFilePath.getText().trim();
        ActionType actionType = ActionType.DOWNLOAD;

        if(path.isEmpty()){
            showError("Invalid File","Please select a file");
            return;
        }

        hashOutputArea.getChildren().clear();

        if(md5Check.isSelected()){
            hashFileAsync(path,"MD5",actionType);
        }
        if(sha1Check.isSelected()){
            hashFileAsync(path,"SHA-1",actionType);
        }
        if(sha256Check.isSelected()){
            hashFileAsync(path,"SHA-256",actionType);
        }
        if(sha512Check.isSelected()){
            hashFileAsync(path,"SHA-512",actionType);
        }
    }


    }

    /// ///////////////////////////////////////////////////////////////


    //Create output pane
    private void hashTextAsync(String input,String algorithm,ActionType actionType){
        Task<String> task = new Task<>() {
            @Override
            protected String call() {
                TextHashService service = new TextHashService(input,algorithm);
                service.execute();
                return service.getHashedText();
            }
        };

        task.setOnSucceeded(e -> {
            hashOutputArea.getChildren().add(
                    new HashOutput(algorithm, task.getValue(), actionType)
            );
        });

        task.setOnFailed(e ->{
            showError("Hash failed",task.getException().getMessage());
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }


    private void hashFileAsync(String path, String algorithm,ActionType actionType) {
        Thread thread = new Thread(() -> {
            try{
                FileHashService service = new FileHashService(path,algorithm);
                service.execute();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        hashOutputArea.getChildren().add(
                                new HashOutput(algorithm,service.getHashedText(),actionType));
                    }
                });
            } catch (Exception e) {
                Platform.runLater(()->
                        showError("Hash failed",e.getMessage())
                );
            }
        });

        thread.setDaemon(true);
        thread.start();
    }


    //Change input mode
    @FXML private StackPane hashInputStack;
    @FXML private VBox hashTextPane;
    @FXML private VBox hashFilePane;
    private InputModeSwitcher hashController;

    @FXML private void initialize(){
        hashController = new InputModeSwitcher(hashInputStack,hashTextPane,hashFilePane);
        hashController.switchToTextMode();

        hashModeGroup.selectedToggleProperty().addListener((obs,oldVal,newVal) -> {
            if(newVal == hashFileModeBtn){
                hashController.switchToFileMode();
            } else if (newVal == hashTextModeBtn) {
                hashController.switchToTextMode();
            }
        });

    }


    //Browse
    @FXML public void handleHashFileSelect(){hashFilePath.setText(selectFile());}

    @Override
    public void reset() {
        hashInput.clear();
        hashFilePath.clear();
        hashOutputArea.getChildren().clear();

        md5Check.setSelected(true);
        sha1Check.setSelected(true);
        sha256Check.setSelected(true);
        sha512Check.setSelected(false);

        hashTextModeBtn.setSelected(true);
        hashController.switchToTextMode();
    }

    @FXML
    public void handleReset(){
        reset();
    }
}
