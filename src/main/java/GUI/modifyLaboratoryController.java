package GUI;
import Controller.Controller;
import Model.Employee;
import Model.Laboratory;
import Model.Project;
import com.sun.jdi.ArrayReference;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
           SrespModifyLaboratory.getItems().add(employee.getSSN());
        }
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        projectModifyLaboratory.getItems().add("Null");
        for (Project project:projectArrayList) {
            projectModifyLaboratory.getItems().add(project.getCup());
        }
    }
    @FXML void modifyLaboratory(int index) {

    }

}
