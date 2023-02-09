package GUI;
import Model.CareerDevelopment;
import Model.Employee;
import Model.Laboratory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.ArrayList;
import Controller.Controller;
public class modifyEmployeeController {
    @FXML private TextField emailModifyEmployee;
    @FXML private TextField firstNameModifyEmployee;
    @FXML private TextField addressModifyEmployee;
    @FXML private ChoiceBox<String> labModifyEmployee;
    @FXML private TextField lastNameModifyEmployee;
    @FXML private Button modifyEmployeeButton;
    @FXML private TextField phoneNumberModifyEmployee;
    @FXML private ChoiceBox<String> roleModifyEmployee;
    @FXML private TextField salaryModifyEmployee;
    @FXML private TextField ssnModifyEmployee;
    Controller controller;
    int index;
    public void setEmployeeIndex(int index)  {
        controller=Controller.getInstance();
        this.index=index;
        String[] roles = {"Junior","Executive"};
        roleModifyEmployee.getItems().addAll(roles);
        ArrayList<Laboratory> labs= controller.getLaboratoryController().getLaboratoryArrayList();
        labModifyEmployee.getItems().add("Null");
        for (Laboratory lab: labs){
            labModifyEmployee.getItems().add(lab.getName());
        }
        ArrayList<Employee>employeeArrayList=controller.getEmployeeController().getEmployeeArrayList();
        firstNameModifyEmployee.setText(employeeArrayList.get(index).getFirstName());
        lastNameModifyEmployee.setText(employeeArrayList.get(index).getLastName());
        phoneNumberModifyEmployee.setText(employeeArrayList.get(index).getPhoneNum());
        addressModifyEmployee.setText(employeeArrayList.get(index).getAddress());
        roleModifyEmployee.setValue(employeeArrayList.get(index).getRole());
        labModifyEmployee.setValue(employeeArrayList.get(index).getLabName());
        salaryModifyEmployee.setText(Float.toString(employeeArrayList.get(index).getSalary()));
        ssnModifyEmployee.setText(employeeArrayList.get(index).getSSN());
        emailModifyEmployee.setText(employeeArrayList.get(index).getEmail());
    }
    @FXML void modifyEmployee() {
        boolean check = true;
        if (ssnModifyEmployee.getText().length() != 15) {
            check = false;
        } else {
            for (char c : ssnModifyEmployee.getText().toCharArray()) {
                if (!Character.isDigit(c)) {
                    check = false;
                    break;
                }
            }
        }
        if(firstNameModifyEmployee.getText().length()>30){
            check=false;
        }else {
            for (char c : firstNameModifyEmployee.getText().toCharArray()) {
                if (Character.isDigit(c)) {
                    check = false;
                    break;
                }
            }
        }
        if(lastNameModifyEmployee.getText().length()>30){
            check=false;
        }else {
            for (char c : lastNameModifyEmployee.getText().toCharArray()) {
                if (Character.isDigit(c)) {
                    check = false;
                    break;
                }
            }
        }
        if (phoneNumberModifyEmployee.getText().length() != 10) {
            check = false;
        } else {
            for (char c : phoneNumberModifyEmployee.getText().toCharArray()) {
                if (!Character.isDigit(c)) {
                    check = false;
                    break;
                }
            }
        }
        if (check) {
            CareerDevelopment careerDevelopment=controller.getEmployeeController().modifyEmployeeList(index,
                    firstNameModifyEmployee.getText(),
                    lastNameModifyEmployee.getText(),
                    phoneNumberModifyEmployee.getText(),
                    roleModifyEmployee.getValue(),
                    salaryModifyEmployee.getText(),
                    labModifyEmployee.getValue(),
                    addressModifyEmployee.getText(),
                    emailModifyEmployee.getText());
            if (careerDevelopment!=null){
                CareerDevelopmentListController.list.add(careerDevelopment);
            }
            Stage stage = (Stage) modifyEmployeeButton.getScene().getWindow();
            stage.close();
        }
    }
}
