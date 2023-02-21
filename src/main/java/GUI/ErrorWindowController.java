package GUI;
import Controller.Controller;
import Model.Laboratory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ErrorWindowController{

    @FXML
    private TextArea ErrorText;

    public void setErrors(ArrayList<String> errorList){
        for(String error: errorList){
            ErrorText.appendText(error + "\n");
        }
    }

}
