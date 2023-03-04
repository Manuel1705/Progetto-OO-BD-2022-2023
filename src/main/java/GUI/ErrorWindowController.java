package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.ArrayList;


public class ErrorWindowController{

    @FXML
    private Label errorLabel= new Label("");

    /**
     * Metodo che inizializza la finestra con i messaggi di errore passati in input.
     * @param errorList Lista dei messaggi di errore.
     */
    public void setErrors(ArrayList<String> errorList){
        for(String error: errorList){
            errorLabel.setText(errorLabel.getText()+"\n"+error);
        }
    }



}
