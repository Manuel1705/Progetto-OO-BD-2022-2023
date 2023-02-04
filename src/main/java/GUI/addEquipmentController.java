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
    Controller controller;
    @FXML void buyEquipment() {
            if(!idEquipmentAddEquipment.getText().isBlank() &&
            !nameAddEquipment.getText().isBlank() &&
            !priceAddEquipment.getText().isBlank() &&
            !dealerAddEquipment.getText().isBlank()){
                controller.getEquipmentController().addEquipmentList(idEquipmentAddEquipment.getText(),
                        nameAddEquipment.getText(),
                        descriptionAddEquipment.getText(),
                        priceAddEquipment.getText(),
                        dealerAddEquipment.getText(),
                        labAddEquipment.getValue(),
                        projectAddEquipment.getValue());
                Stage stage = (Stage) buyEquipmentButton.getScene().getWindow();
                stage.close();
            }
    }
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        controller=Controller.getInstance();
        ArrayList<Laboratory> labs= controller.getLaboratoryController().getLaboratoryArrayList();
        labAddEquipment.getItems().add("Null");
        for (Laboratory lab: labs) {
            labAddEquipment.getItems().add(lab.getName());
        }
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        for (Project project:projectArrayList) {
            projectAddEquipment.getItems().add(project.getCup());
        }

    }

}
