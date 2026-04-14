package logic.superClass;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class InputModeSwitcher {

    @FXML protected StackPane inputStack;
    @FXML protected VBox textPane;
    @FXML protected VBox filePane;

    public InputModeSwitcher(StackPane inputStack, VBox textPane, VBox filePane){
        this.inputStack = inputStack;
        this.textPane = textPane;
        this.filePane = filePane;
    }

    public void switchToTextMode() {
        textPane.setVisible(true);
        textPane.setManaged(true);

        filePane.setVisible(false);
        filePane.setManaged(false);
    }

    public void switchToFileMode() {
        textPane.setVisible(false);
        textPane.setManaged(false);

        filePane.setVisible(true);
        filePane.setManaged(true);
    }
}
