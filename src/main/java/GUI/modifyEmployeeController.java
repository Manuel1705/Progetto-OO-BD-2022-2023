/*package GUI;

import Model.Employee;
import Model.Laboratory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.ArrayList;

public class modifyEmployeeController{


    @FXML
    private TextField emailModifyEmployee;

    @FXML
    private TextField firstNameModifyEmployee;
    @FXML
    private TextField addressModifyEmployee;
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

    public void setEmployeeIndex(int index)  {
       this.index=index;
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
        emailModifyEmployee.setText(EmployeeListController.list.get(index).getEmail());
    }

    @FXML
    void modifyEmployee(){
        Employee employee = EmployeeListController.list.get(index);
        employee.setFirstName(firstNameModifyEmployee.getText());
        employee.setLastName(lastNameModifyEmployee.getText());
        employee.setPhoneNum(phoneNumberModifyEmployee.getText());
        employee.setRole(roleModifyEmployee.getValue());
        employee.setSalary(Float.parseFloat(salaryModifyEmployee.getText()));
        int i=0;
        while (!LaboratoryListController.list.get(i).getName().equals(labModifyEmployee.getValue())) {
            i++;
        }
        employee.setLab(LaboratoryListController.list.get(i));
        if(!addressModifyEmployee.getText().isEmpty()){
            employee.setAddress(addressModifyEmployee.getText());
        }
        if(!emailModifyEmployee.getText().isEmpty()){
            employee.setEmail(emailModifyEmployee.getText());
        }
        Stage stage = (Stage) modifyEmployeeButton.getScene().getWindow();
        stage.close();
    }
}*/
