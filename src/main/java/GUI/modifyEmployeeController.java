package GUI;

import Model.Employee;
import Model.Laboratory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class modifyEmployeeController implements Initializable {

    @FXML
    private TextField addressModifyEmployee;

    @FXML
    private TextField emailModifyEmployee;

    @FXML
    private TextField firstNameModifyEmployee;

    @FXML
    private ChoiceBox<String> labModifyEmployee;

    @FXML
    private TextField lastNameModifyEmployee;

    @FXML
    private Button modifyEmployeeButton;

    @FXML
    private TextField phoneNumberModifyEmployee;

    @FXML
    private ChoiceBox<String> roleModifyEmployee;

    @FXML
    private TextField salaryModifyEmployee;

    @FXML
    private TextField ssnModifyEmployee;
    int index;
    public void setEmployeeIndex(int index){
        this.index=index;
    }
    @FXML
    void modifyEmployee(){
        Employee employee = EmployeeListController.list.get(index);
        employee.setFirstName(firstNameModifyEmployee.getText());
        employee.setLastName(lastNameModifyEmployee.getText());
        employee.setPhoneNum(phoneNumberModifyEmployee.getText());
        employee.setRole(roleModifyEmployee.getValue());
        employee.setSalary(Float.valueOf(salaryModifyEmployee.getText()));
        if(!labModifyEmployee.getValue().isBlank()){
            employee.setLabName(labModifyEmployee.getValue());
        }else {
            employee.setLabName(null);
        }
        if(!addressModifyEmployee.getText().isEmpty()){
            employee.setAddress(addressModifyEmployee.getText());
        }
        if(!emailModifyEmployee.getText().isEmpty()){
            employee.setEmail(emailModifyEmployee.getText());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] roles={"Junior","Executive"};
        roleModifyEmployee.getItems().addAll(roles);
        ArrayList<String> labs= new ArrayList<String>();
        labs.add(null);
        for (Laboratory lab: LaboratoryListController.list
        ) {
            labs.add(lab.getName());
        }
        labModifyEmployee.getItems().addAll(labs);
        firstNameModifyEmployee.setText(EmployeeListController.list.get(index).getFirstName());
        lastNameModifyEmployee.setText(EmployeeListController.list.get(index).getLastName());
        phoneNumberModifyEmployee.setText(EmployeeListController.list.get(index).getPhoneNum());
        addressModifyEmployee.setText(EmployeeListController.list.get(index).getAddress());
        roleModifyEmployee.setValue(EmployeeListController.list.get(index).getRole());
        labModifyEmployee.setValue(EmployeeListController.list.get(index).getLabName());
        salaryModifyEmployee.setText(Float.toString(EmployeeListController.list.get(index).getSalary()));
        ssnModifyEmployee.setText(EmployeeListController.list.get(index).getSsn());
    }
}
