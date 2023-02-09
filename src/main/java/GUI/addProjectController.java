package GUI;
import Controller.Controller;
import Model.Employee;
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
    @FXML void addProject() {
        if(!cupAddProject.getText().isBlank()&&
            !nameAddProject.getText().isBlank()&&
            !budgetAddProject.getText().isBlank()&&
            endDateAddProject.getValue().compareTo(LocalDate.now())>0) {
            boolean check = true;
            if (cupAddProject.getText().length() != 15) {
                check = false;
            }
            if(nameAddProject.getText().length()>30){
                check=false;
            }else {
                for (char c : nameAddProject.getText().toCharArray()) {
                    if (Character.isDigit(c)) {
                        check = false;
                        break;
                    }
                }
            }
            if (check) {
                controller.getProjectController().addProjectList(
                        cupAddProject.getText(),
                        nameAddProject.getText(),
                        budgetAddProject.getText(),
                        endDateAddProject.getValue(),
                        SrespAddProject.getValue(),
                        SrefAddProject.getValue());

                Stage stage = (Stage) addProjectButton.getScene().getWindow();
                stage.close();
            }
        }
    }
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        controller=Controller.getInstance();
        ArrayList<Employee> employeeArrayList=controller.getEmployeeController().getEmployeeArrayList();
        SrefAddProject.getItems().add("Empty Position");
        SrespAddProject.getItems().add("Empty Position");
        for (Employee employee: employeeArrayList) {
            if(employee.getRole().equals("Executive")){
                SrespAddProject.getItems().add(employee.getSSN());
            }
            else if(employee.getRole().equals("Senior")) {
                SrefAddProject.getItems().add(employee.getSSN());
                }
        }

    }
}

