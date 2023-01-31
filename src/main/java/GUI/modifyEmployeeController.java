package GUI;
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
        this.index=index;
        String[] roles = {"Junior","Executive"};
        roleModifyEmployee.getItems().addAll(roles);
        ArrayList<String> labs= new ArrayList<String>();
        labs.add("Null");
        for (Laboratory lab: LaboratoryListController.list){
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
        ssnModifyEmployee.setText(EmployeeListController.list.get(index).getSSN());
        emailModifyEmployee.setText(EmployeeListController.list.get(index).getEmail());
    }
    @FXML void modifyEmployee(){
        controller=Controller.getInstance();
        controller.getEmployeeController().modifyEmployeeList(index,
                firstNameModifyEmployee.getText(),
                lastNameModifyEmployee.getText(),
                phoneNumberModifyEmployee.getText(),
                roleModifyEmployee.getValue(),
                salaryModifyEmployee.getText(),
                labModifyEmployee.getValue(),
                addressModifyEmployee.getText(),
                emailModifyEmployee.getText());

        Stage stage = (Stage) modifyEmployeeButton.getScene().getWindow();
        stage.close();
    }
}
