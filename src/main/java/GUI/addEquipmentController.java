package GUI;
import Controller.Controller;
import Model.Laboratory;
import Model.Project;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class addEquipmentController implements Initializable {
    @FXML private Button buyEquipmentButton;
    @FXML private TextField dealerAddEquipment;
    @FXML private TextArea descriptionAddEquipment;
    @FXML private TextField idEquipmentAddEquipment;
    @FXML private ChoiceBox<String> labAddEquipment;
    @FXML private TextField nameAddEquipment;
    @FXML private TextField priceAddEquipment;
    @FXML private ChoiceBox<String> projectAddEquipment;
    private Controller controller;

    /**
     * Metodo che viene chiamato quando l'utente conferma l'inserimento dei dati. Effettua i controlli sulla validita' dell'input.
     */
    @FXML void buyEquipment() {
            if(!idEquipmentAddEquipment.getText().isBlank() &&
            !nameAddEquipment.getText().isBlank() &&
            !priceAddEquipment.getText().isBlank() &&
            !dealerAddEquipment.getText().isBlank()) {
                boolean check = true;
                //Controllo sulla lunghezza dell'input
                if (nameAddEquipment.getText().length() > 30) {
                    check = false;
                }
                //Controllo sulla lunghezza del nome del fornitore
                if (dealerAddEquipment.getText().length() > 30) {
                    check = false;
                }
                //Controllo sulla lunghezza del nome
                if (nameAddEquipment.getText().length() > 30) {
                    check = false;
                }
                //Controllo sulla lunghezza della descrizione
                if (descriptionAddEquipment.getText().length() > 200) {
                    check = false;
                }
                if (check) {
                    controller.getEquipmentController().addEquipmentList(Integer.parseInt(idEquipmentAddEquipment.getText()),
                            nameAddEquipment.getText(),
                            descriptionAddEquipment.getText(),
                            Float.parseFloat(priceAddEquipment.getText()),
                            dealerAddEquipment.getText(),
                            labAddEquipment.getValue(),
                            projectAddEquipment.getValue());
                    Stage stage = (Stage) buyEquipmentButton.getScene().getWindow();
                    stage.close();
                }
            }
    }

    /**
     * Metodo che inizializza la finestra.
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        controller=Controller.getInstance();
        //Il metodo inizializza il menu' a tendina dei laboratori.
        ArrayList<Laboratory> labs = controller.getLaboratoryController().getLaboratoryArrayList();
        labAddEquipment.getItems().add("Null");
        for (Laboratory lab: labs) {
            labAddEquipment.getItems().add(lab.getName());
        }
        //Il metodo inizializza il menu' a tendina dei progetti.
        ArrayList<Project>projectArrayList = controller.getProjectController().getProjectArrayList();
        for (Project project:projectArrayList) {
            projectAddEquipment.getItems().add(project.getCup());
        }

    }

}
