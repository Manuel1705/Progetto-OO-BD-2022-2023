package GUI;
import Controller.Controller;
import Model.Employee;
import Model.Laboratory;
import Model.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class modifyLaboratoryController {
    @FXML private ChoiceBox<String> SrespModifyLaboratory;
    @FXML private Button modifyLaboratoryButton;
    @FXML private TextField nameModifyLaboratory;
    @FXML private ChoiceBox<String> projectModifyLaboratory;
    @FXML private TextArea topicModifyLaboratory;
    Controller controller;
    int index;
    public void setLaboratoryIndex(int index){
        this.index=index;
        controller=Controller.getInstance();
        ArrayList<Employee>employeeArrayList=controller.getEmployeeController().getEmployeeArrayList();
        SrespModifyLaboratory.getItems().add("Null");
        for (Employee employee:employeeArrayList) {
            if(employee.getRole().equals("Senior"))
           SrespModifyLaboratory.getItems().add(employee.getSSN());
        }
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        projectModifyLaboratory.getItems().add("Null");
        for (Project project:projectArrayList) {
            projectModifyLaboratory.getItems().add(project.getCup());
        }
        ArrayList<Laboratory>laboratoryArrayList=controller.getLaboratoryController().getLaboratoryArrayList();
        topicModifyLaboratory.setText(laboratoryArrayList.get(index).getTopic());
        nameModifyLaboratory.setText(laboratoryArrayList.get(index).getName());
        projectModifyLaboratory.setValue(laboratoryArrayList.get(index).getProjectCup());
        SrespModifyLaboratory.setValue(laboratoryArrayList.get(index).getSrespSSN());
    }
    @FXML void modifyLaboratory() {
                controller.getLaboratoryController().modifyLaboratory(index,
                        topicModifyLaboratory.getText(),
                        SrespModifyLaboratory.getValue(),
                        projectModifyLaboratory.getValue());
        Stage stage = (Stage) modifyLaboratoryButton.getScene().getWindow();
        stage.close();
    }

}
