package application;
//fx:controller="service.portScanner.PortScannerController"
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class Application extends javafx.application.Application {

   @FXML private TextField scannerIP;
    @FXML private TextField scannerStartPort;
    @FXML private TextField scannerEndPort;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Main.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Network Toolkit");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}




