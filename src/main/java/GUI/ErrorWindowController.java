package GUI;
import Controller.Controller;
import Model.Laboratory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ErrorWindowController{

    @FXML
    private Label errorLabel= new Label("");

    /**
     * Metodo che inizializza la finestra con i messaggi di errore passati in inpyt.
     * @param errorList Lista dei messagi di errore.
     */
    public void setErrors(ArrayList<String> errorList){
        for(String error: errorList){
            errorLabel.setText(errorLabel.getText()+"\n"+error);
        }
    }



}
