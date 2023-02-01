package GUI;
import Controller.Controller;
import Model.Employee;
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
public class addLaboratoryController implements Initializable {
    @FXML private ChoiceBox<String> SrespAddLaboratory;
    @FXML private Button addLaboratoryButton;
    @FXML private TextField nameAddLaboratory;
    @FXML private ChoiceBox<String> projectAddLaboratory;
    @FXML private TextArea topicAddLaboratory;
    Controller controller;
    @FXML void addLaboratory() {
        if (!nameAddLaboratory.getText().isBlank())
        {
                    controller.getLaboratoryController().addLaboratoryList(
                    nameAddLaboratory.getText(),
                    topicAddLaboratory.getText(),
                    SrespAddLaboratory.getValue(),
                    projectAddLaboratory.getValue());

            Stage stage = (Stage) addLaboratoryButton.getScene().getWindow();
            stage.close();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller=Controller.getInstance();
        ArrayList<Employee> employeeArrayList=controller.getEmployeeController().getEmployeeArrayList();
        ArrayList<String> Sresp= new ArrayList<>();
        Sresp.add("Null");
        for (Employee employee: employeeArrayList) {
            if(employee.getRole().equals("Senior")) {
                Sresp.add(employee.getSSN());
            }
        }
        SrespAddLaboratory.getItems().addAll(Sresp);
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        ArrayList<String>projects= new ArrayList<>();
        projects.add("Null");
        for (Project project: projectArrayList) {
            projects.add(project.getCup());
        }
        projectAddLaboratory.getItems().addAll(projects);
    }
}

