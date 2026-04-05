package logic.superClass;

import javafx.scene.control.Alert;

public class BaseController {

    protected void showError(String title,String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.show();
    }

    protected void showSuccess(String text){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Task Done");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.show();
    }
}
