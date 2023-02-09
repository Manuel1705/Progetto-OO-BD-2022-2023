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
    Controller controller;
    int index;
    public void setEquipmentIndex(int index){
        controller=Controller.getInstance();
        this.index=index;
        ArrayList<Laboratory> labs= controller.getLaboratoryController().getLaboratoryArrayList();
        labModifyEquipment.getItems().add("Null");
        for (Laboratory lab: labs) {
            labModifyEquipment.getItems().add(lab.getName());
        }
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        projectModifyEquipment.getItems().add("Null");
        for (Project project:projectArrayList) {
            projectModifyEquipment.getItems().add(project.getCup());
        }
        ArrayList<Equipment>equipmentArrayList=controller.getEquipmentController().getEquipmentArrayList();
        idEquipmentModifyEquipment.setText(equipmentArrayList.get(index).getId());
        nameModifyEquipment.setText(equipmentArrayList.get(index).getName());
        dealerModifyEquipment.setText(equipmentArrayList.get(index).getDealer());
        descriptionModifyEquipment.setText(equipmentArrayList.get(index).getDescription());
        priceModifyEquipment.setText(Float.toString(equipmentArrayList.get(index).getPrice()));
        labModifyEquipment.setValue(equipmentArrayList.get(index).getLabName());
        projectModifyEquipment.setValue(equipmentArrayList.get(index).getProjectCup());
    }
    @FXML void modifyEquipment() {
        boolean check = true;
        if (nameModifyEquipment.getText().length() > 30) {
            check = false;
        } else {
            for (char c : nameModifyEquipment.getText().toCharArray()) {
                if (Character.isDigit(c)) {
                    check = false;
                    break;
                }
            }
        }
        if (check) {
            controller.getEquipmentController().modifyEquipment(index,
                    nameModifyEquipment.getText(),
                    descriptionModifyEquipment.getText(),
                    labModifyEquipment.getValue(),
                    projectModifyEquipment.getValue());
            Stage stage = (Stage) modifyEquipmentButton.getScene().getWindow();
            stage.close();
        }
    }
}
