package GUI;
import Controller.Controller;
import Model.Employee;
import Model.Laboratory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class addEmployeeController implements Initializable {

    @FXML
    private TextField lastNameAddEmployee;

    @FXML
    private TextField addressAddEmployee;

    @FXML
    private TextField emailAddEmployee;

    @FXML
    private TextField firstNameAddEmployee;

    @FXML
    private Button hireEmployeeButton;

    @FXML
    private ChoiceBox<String> labAddEmployee;

    @FXML
    private TextField phoneNumberAddEmployee;

    @FXML
    private ChoiceBox<String> roleAddEmployee;

    @FXML
    private TextField salaryAddEmployee;

    @FXML
    private TextField ssnAddEmployee;
    Controller controller;
    @FXML
    void hireEmployee() throws IOException {
        if(!roleAddEmployee.getValue().isBlank() &&
                !ssnAddEmployee.getText().isBlank() &&
                !firstNameAddEmployee.getText().isBlank() &&
                !lastNameAddEmployee.getText().isBlank()&&
                !salaryAddEmployee.getText().isBlank() &&
                !phoneNumberAddEmployee.getText().isBlank()) {
                    controller=Controller.getInstance();
                    controller.getEmployeeController().addEmployeeList(ssnAddEmployee.getText(),
                    firstNameAddEmployee.getText(),
                    lastNameAddEmployee.getText(),
                    phoneNumberAddEmployee.getText(),
                    addressAddEmployee.getText(),
                    roleAddEmployee.getValue(),
                    emailAddEmployee.getText(),
                    LocalDate.now(),
                    Float.parseFloat(salaryAddEmployee.getText()),
                    labAddEmployee.getValue());

                    Stage stage = (Stage) hireEmployeeButton.getScene().getWindow();
                    stage.close();
                }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String[] roles={"Junior","Executive"};
        roleAddEmployee.getItems().addAll(roles);

        ArrayList<String> labs= new ArrayList<String>();
        labs.add("Null");
        for (Laboratory lab: LaboratoryListController.list
             ) {
            labs.add(lab.getName());
        }
        labAddEmployee.getItems().addAll(labs);
    }
}
