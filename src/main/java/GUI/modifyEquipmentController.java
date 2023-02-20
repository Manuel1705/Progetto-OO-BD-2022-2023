package GUI;
import Controller.Controller;
import Model.Equipment;
import Model.Laboratory;
import Model.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.ArrayList;
public class modifyEquipmentController {
    @FXML private TextField dealerModifyEquipment;
    @FXML private TextArea descriptionModifyEquipment;
    @FXML private TextField idEquipmentModifyEquipment;
    @FXML private ChoiceBox<String> labModifyEquipment;
    @FXML private Button modifyEquipmentButton;
    @FXML private TextField nameModifyEquipment;
    @FXML private TextField priceModifyEquipment;
    @FXML private ChoiceBox<String> projectModifyEquipment;
    private Controller controller;
    int id;

    /**
     * Metodo che inizializza i campi della finestra.
     * @param id
     * @param description
     * @param dealer
     * @param labName
     * @param projectCup
     * @param price
     * @param name
     */
    public void setDefaultFields(int id, String description, String dealer, String labName, String projectCup, float price,
                                  String name){
        controller=Controller.getInstance();
        this.id = id;
        ArrayList<Laboratory> labs = controller.getLaboratoryController().getLaboratoryArrayList();
        labModifyEquipment.getItems().add("Null");
        for (Laboratory lab: labs) {
            labModifyEquipment.getItems().add(lab.getName());
        }
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        projectModifyEquipment.getItems().add("Null");
        for (Project project:projectArrayList) {
            projectModifyEquipment.getItems().add(project.getCup());
        }

        idEquipmentModifyEquipment.setText(Integer.toString(id));
        nameModifyEquipment.setText(name);
        dealerModifyEquipment.setText(dealer);
        descriptionModifyEquipment.setText(description);
        priceModifyEquipment.setText(Float.toString(price));
        labModifyEquipment.setValue(labName);
        projectModifyEquipment.setValue(projectCup);
    }

    /**
     * Metodo che viene chiamato quando l'utente conferma la modifica.
     */
    @FXML void modifyEquipment() {
        if(!idEquipmentModifyEquipment.getText().isBlank() &&
                !nameModifyEquipment.getText().isBlank() &&
                !priceModifyEquipment.getText().isBlank() &&
                !dealerModifyEquipment.getText().isBlank()) {
            boolean check = true;
            //Controllo sulla lunghezza dell'input
            if (nameModifyEquipment.getText().length() > 30) {
                check = false;
            }
            //Controllo sulla lunghezza del nome del fornitore
            if (dealerModifyEquipment.getText().length() > 30) {
                check = false;
            }
            //Controllo sulla lunghezza del nome
            if (nameModifyEquipment.getText().length() > 30) {
                check = false;
            }
            //Controllo sulla lunghezza della descrizione
            if (descriptionModifyEquipment.getText().length() > 200) {
                check = false;
            }
            if (check) {
                controller.getEquipmentController().modifyEquipment(id,
                        nameModifyEquipment.getText(),
                        descriptionModifyEquipment.getText(),
                        labModifyEquipment.getValue(),
                        projectModifyEquipment.getValue());
                Stage stage = (Stage) modifyEquipmentButton.getScene().getWindow();
                stage.close();
            }
        }
    }
}
