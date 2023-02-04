package GUI;
import Controller.Controller;
import Model.Laboratory;
import Model.Project;
import Model.TemporaryEmployee;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class modifyTemporaryEmployeeController {

    @FXML
    private TextField addressModifyTemporaryEmployee;

    @FXML
    private TextField emailModifyTemporaryEmployee;

    @FXML
    private TextField firstNameModifyTemporaryEmployee;

    @FXML
    private ChoiceBox<String> labModifyTemporaryEmployee;

    @FXML
    private TextField lastNameModifyTemporaryEmployee;

    @FXML
    private Button modifyTemporaryEmployeeButton;

    @FXML
    private TextField phoneNumberModifyTemporaryEmployee;

    @FXML
    private ChoiceBox<String> projectModifyTemporaryEmployee;

    @FXML
    private TextField salaryModifyTemporaryEmployee;

    @FXML
    private TextField ssnModifyTemporaryEmployee;
    Controller controller;
    int index;
    public void setTemporaryEmployeeIndex(int index)  {
    controller=Controller.getInstance();
    this.index=index;
    ArrayList<Laboratory> labs= controller.getLaboratoryController().getLaboratoryArrayList();
    labModifyTemporaryEmployee.getItems().add("Null");
    for (Laboratory lab: labs){
        labModifyTemporaryEmployee.getItems().add(lab.getName());
    }
        ArrayList<Project> projects= controller.getProjectController().getProjectArrayList();
        for (Project prj: projects){
            projectModifyTemporaryEmployee.getItems().add(prj.getName());
        }
    ArrayList<TemporaryEmployee>employeeArrayList=controller.getTemporaryEmployeeController().getTemporaryEmployeeArrayList();
    firstNameModifyTemporaryEmployee.setText(employeeArrayList.get(index).getFirstName());
    lastNameModifyTemporaryEmployee.setText(employeeArrayList.get(index).getLastName());
    phoneNumberModifyTemporaryEmployee.setText(employeeArrayList.get(index).getPhoneNum());
    addressModifyTemporaryEmployee.setText(employeeArrayList.get(index).getAddress());
    labModifyTemporaryEmployee.setValue(employeeArrayList.get(index).getLabName());
    salaryModifyTemporaryEmployee.setText(Float.toString(employeeArrayList.get(index).getSalary()));
    ssnModifyTemporaryEmployee.setText(employeeArrayList.get(index).getSSN());
    emailModifyTemporaryEmployee.setText(employeeArrayList.get(index).getEmail());
    projectModifyTemporaryEmployee.setValue(employeeArrayList.get(index).getProjectCup());
}
    @FXML
    void modifyTemporaryEmployee() {
        controller.getTemporaryEmployeeController().modifyTemporaryEmployeeList(index,
                firstNameModifyTemporaryEmployee.getText(),
                lastNameModifyTemporaryEmployee.getText(),
                phoneNumberModifyTemporaryEmployee.getText(),
                salaryModifyTemporaryEmployee.getText(),
                labModifyTemporaryEmployee.getValue(),
                addressModifyTemporaryEmployee.getText(),
                emailModifyTemporaryEmployee.getText(),
                projectModifyTemporaryEmployee.getValue());
        Stage stage = (Stage) modifyTemporaryEmployeeButton.getScene().getWindow();
        stage.close();
    }

}
