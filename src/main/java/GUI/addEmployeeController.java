package GUI;

import Model.Employee;
import Model.Laboratory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    @FXML
    void hireEmployee(ActionEvent event) throws IOException {
                EmployeeListController employeeListController = new EmployeeListController();
                Employee employee = new Employee(ssnAddEmployee.getText(),
                        firstNameAddEmployee.getText(),
                        lastNameAddEmployee.getText(),
                        phoneNumberAddEmployee.getText(),
                        roleAddEmployee.getValue(),
                        Float.parseFloat(salaryAddEmployee.getText()));
                if(!labAddEmployee.getValue().equals("Null")){
                    employee.setLabName(labAddEmployee.getValue());
                    int i=0;
                    while (!LaboratoryListController.list.get(i).getName().equals(labAddEmployee.getValue())) {
                        i++;
                    }
                    employee.setLab(LaboratoryListController.list.get(i));
                }
                if(!addressAddEmployee.getText().isEmpty()){
                    employee.setAddress(addressAddEmployee.getText());
                }
                if(!emailAddEmployee.getText().isEmpty()){
                    employee.setEmail(emailAddEmployee.getText());
                }
                if(!roleAddEmployee.getValue().isBlank() &&
                        !employee.getSsn().isBlank() &&
                    !employee.getFirstName().isBlank() &&
                    !employee.getLastName().isBlank() &&
                    !Float.toString(employee.getSalary()).isBlank() &&
                    !employee.getPhoneNum().isBlank()) {
                        employeeListController.addEmployeeList(employee);
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
