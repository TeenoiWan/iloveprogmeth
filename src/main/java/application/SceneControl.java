package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class SceneControl {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private AnchorPane contentArea;

    public void switchToPortScanner(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/portscanner/PortScannerTest.fxml"));
        contentArea.getChildren().setAll(root);
    }

    public void switchToEncryption(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/encryption/Encryption.fxml"));
        contentArea.getChildren().setAll(root);
    }

    public void switchToHash(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/hashgenerator/HashGenerator.fxml"));
        contentArea.getChildren().setAll(root);
    }
}
