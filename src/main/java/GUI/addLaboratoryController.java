package GUI;

import Controller.Controller;
import Model.Employee;
import Model.Laboratory;
import Model.Project;
import javafx.event.ActionEvent;
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

    @FXML
    private ChoiceBox<String> SrespAddLaboratory;

    @FXML
    private Button addLaboratoryButton;

    @FXML
    private TextField nameAddLaboratory;

    @FXML
    private ChoiceBox<String> projectAddLaboratory;

    @FXML
    private TextArea topicAddLaboratory;
    Controller controller;

    @FXML
    void addLaboratory() {
        if (!nameAddLaboratory.getText().isBlank() &&
                !topicAddLaboratory.getText().isBlank()) {
            controller = Controller.getInstance();
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

        ArrayList<String> Sresp= new ArrayList<>();
        Sresp.add("Null");
        for (Employee employee: EmployeeListController.list) {
            Sresp.add(employee.getSsn());
        }
        SrespAddLaboratory.getItems().addAll(Sresp);
        ArrayList<String>projects= new ArrayList<>();
        projects.add("Null");
        for (Project project: ProjectListController.list) {
            projects.add(project.getName());
        }
        projectAddLaboratory.getItems().addAll(projects);
    }
}

