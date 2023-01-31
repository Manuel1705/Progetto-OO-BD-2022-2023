package GUI;
import Controller.Controller;
import Model.Employee;
import Model.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class addProjectController implements Initializable {
    @FXML private Button addProjectButton;
    @FXML private TextField budgetAddProject;
    @FXML private TextField cupAddProject;
    @FXML private DatePicker endDateAddProject;
    @FXML private TextField nameAddProject;
    @FXML private ChoiceBox<String> SrefAddProject;
    @FXML private ChoiceBox<String> SrespAddProject;
    Controller controller;
    @FXML void addProject(ActionEvent event) {
        if(!cupAddProject.getText().isBlank()&&
            !nameAddProject.getText().isBlank()&&
            !budgetAddProject.getText().isBlank()&&
            endDateAddProject.getValue().compareTo(LocalDate.now())>0)
        {
            controller=Controller.getInstance();
            controller.getProjectController().addProjectList(
                    cupAddProject.getText(),
                    nameAddProject.getText(),
                    budgetAddProject.getText(),
                    endDateAddProject.getValue(),
                    SrefAddProject.getValue(),
                    SrespAddProject.getValue());

            Stage stage = (Stage) addProjectButton.getScene().getWindow();
            stage.close();
        }
    }
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> SrespSSN = new ArrayList<String>();
        SrespSSN.add("Null");
        ArrayList<String> SrefSSN = new ArrayList<String>();
        SrefSSN.add("Null");
        controller=Controller.getInstance();
        ArrayList<Employee> employeeArrayList=controller.getEmployeeController().getEmployeeArrayList();
        for (Employee employye: employeeArrayList) {
            if(employye.getRole().equals("Executive")){
                SrespSSN.add(employye.getSSN());
            }
            else if(employye.getRole().equals("Senior")) {
                SrefSSN.add(employye.getSSN());
                }
        }
            SrespAddProject.getItems().addAll(SrespSSN);
            SrefAddProject.getItems().addAll(SrefSSN);
    }
    }

