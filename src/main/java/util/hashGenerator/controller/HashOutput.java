package util.hashGenerator.controller;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.input.Clipboard;
import javafx.stage.FileChooser;
import util.hashGenerator.dataType.ActionType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HashOutput extends HBox {

    public HashOutput(String algorithm, String hashedText, ActionType actionType){

        Label algoLabel = new Label(algorithm);
        algoLabel.setPrefWidth(60);

        TextField hashField = new TextField(hashedText);
        hashField.setPrefWidth(240);
        hashField.setEditable(false);

        Button btn = new Button();

        if(actionType == ActionType.COPY){
            btn.setText("Copy");
            btn.setOnAction(e -> copyToClipboard(hashedText));
        }else if(actionType == ActionType.DOWNLOAD){
            btn.setText("Download");
            btn.setOnAction(e -> downLoadToFile(algorithm,hashedText));
        }


        this.setSpacing(10);
        this.setAlignment(Pos.CENTER_LEFT);

        this.getChildren().addAll(algoLabel,hashField,btn);

        this.setOnMouseEntered(e -> this.setStyle("-fx-background-color : #D3D3D3;"));
        this.setOnMouseExited(e -> this.setStyle(""));
    }

    private void copyToClipboard(String text){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    private void downLoadToFile(String algorithm,String content){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Hash");
        fileChooser.setInitialFileName(algorithm + "_hash.txt");

        File file = fileChooser.showSaveDialog(getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            } catch (IOException e) {
                throw new RuntimeException("Fail to save file", e);
            }
        }
    }
}
