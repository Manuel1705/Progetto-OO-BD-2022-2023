package GUI;
import Controller.Controller;
import Model.Laboratory;
import Model.Project;
import Model.TemporaryEmployee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class addTemporaryEmployeeController implements Initializable {

    @FXML
    private TextField addressAddTemporaryEmployee;

    @FXML
    private TextField emailAddTemporaryEmployee;

    @FXML
    private TextField firstNameAddTemporaryEmployee;

    @FXML
    private Button hireTemporaryEmployeeButton;

    @FXML
    private ChoiceBox<String> labAddTemporaryEmployee;

    @FXML
    private TextField lastNameAddTemporaryEmployee;

    @FXML
    private TextField phoneNumberAddTemporaryEmployee;

    @FXML
    private ChoiceBox<String> projectAddTemporaryEmployee;

    @FXML
    private TextField salaryAddTemporaryEmployee;

    @FXML
    private TextField ssnAddTemporaryEmployee;
    Controller controller;

    @FXML
    void hireTemporaryEmployee() {
        if (!ssnAddTemporaryEmployee.getText().isBlank() &&
                !firstNameAddTemporaryEmployee.getText().isBlank() &&
                !lastNameAddTemporaryEmployee.getText().isBlank() &&
                !salaryAddTemporaryEmployee.getText().isBlank() &&
                !phoneNumberAddTemporaryEmployee.getText().isBlank() &&
                !projectAddTemporaryEmployee.getValue().isBlank()) {
            boolean check = true;
            if (ssnAddTemporaryEmployee.getText().length() != 15) {
                check = false;
            } else {
                for (char c : ssnAddTemporaryEmployee.getText().toCharArray()) {
                    if (!Character.isDigit(c)) {
                        check = false;
                        break;
                    }
                }
            }
            if(firstNameAddTemporaryEmployee.getText().length()>30){
                check=false;
            }else {
                for (char c : firstNameAddTemporaryEmployee.getText().toCharArray()) {
                    if (Character.isDigit(c)) {
                        check = false;
                        break;
                    }
                }
            }
            if(lastNameAddTemporaryEmployee.getText().length()>30){
                check=false;
            }else {
                for (char c : lastNameAddTemporaryEmployee.getText().toCharArray()) {
                    if (Character.isDigit(c)) {
                        check = false;
                        break;
                    }
                }
            }
            if (phoneNumberAddTemporaryEmployee.getText().length() != 10) {
                check = false;
            } else {
                for (char c : phoneNumberAddTemporaryEmployee.getText().toCharArray()) {
                    if (!Character.isDigit(c)) {
                        check = false;
                        break;
                    }
                }
            }
            if (check) {
                TemporaryEmployee temporaryEmployee = controller.getTemporaryEmployeeController().addTemporaryEmployeeList(ssnAddTemporaryEmployee.getText(),
                        firstNameAddTemporaryEmployee.getText(),
                        lastNameAddTemporaryEmployee.getText(),
                        phoneNumberAddTemporaryEmployee.getText(),
                        addressAddTemporaryEmployee.getText(),
                        emailAddTemporaryEmployee.getText(),
                        LocalDate.now(),
                        salaryAddTemporaryEmployee.getText(),
                        labAddTemporaryEmployee.getValue(),
                        projectAddTemporaryEmployee.getValue());
                if (temporaryEmployee != null) {
                    TemporaryEmployeeListController.list.add(temporaryEmployee);
                }
                Stage stage = (Stage) hireTemporaryEmployeeButton.getScene().getWindow();
                stage.close();
            }
        }
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        controller=Controller.getInstance();
        ArrayList<Laboratory> labs= controller.getLaboratoryController().getLaboratoryArrayList();
        labAddTemporaryEmployee.getItems().add("Null");
        for (Laboratory lab: labs) {
            labAddTemporaryEmployee.getItems().add(lab.getName());
        }
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        for (Project prj:projectArrayList) {
            projectAddTemporaryEmployee.getItems().add(prj.getCup());
        }
    }
    }

