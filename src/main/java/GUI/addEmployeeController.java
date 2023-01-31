package GUI;
import Controller.Controller;
import Model.Laboratory;
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
public class addEmployeeController implements Initializable {
    @FXML private TextField lastNameAddEmployee;
    @FXML private TextField addressAddEmployee;
    @FXML private TextField emailAddEmployee;
    @FXML private TextField firstNameAddEmployee;
    @FXML private Button hireEmployeeButton;
    @FXML private ChoiceBox<String> labAddEmployee;
    @FXML private TextField phoneNumberAddEmployee;
    @FXML private ChoiceBox<String> roleAddEmployee;
    @FXML private TextField salaryAddEmployee;
    @FXML private TextField ssnAddEmployee;
    Controller controller;
    @FXML void hireEmployee(){
        if(!roleAddEmployee.getValue().isBlank() &&
                !ssnAddEmployee.getText().isBlank() &&
                !firstNameAddEmployee.getText().isBlank() &&
                !lastNameAddEmployee.getText().isBlank()&&
                !salaryAddEmployee.getText().isBlank() &&
                !phoneNumberAddEmployee.getText().isBlank())
        {
                    controller.getEmployeeController().addEmployeeList(
                        ssnAddEmployee.getText(),
                        firstNameAddEmployee.getText(),
                        lastNameAddEmployee.getText(),
                        phoneNumberAddEmployee.getText(),
                        addressAddEmployee.getText(),
                        roleAddEmployee.getValue(),
                        emailAddEmployee.getText(),
                        LocalDate.now(),
                        salaryAddEmployee.getText(),
                        labAddEmployee.getValue());
                    //chiusura finestra pop up
                    Stage stage = (Stage) hireEmployeeButton.getScene().getWindow();
                    stage.close();
                }
    }
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] roles={"Junior","Executive"};
        roleAddEmployee.getItems().addAll(roles);
        controller=Controller.getInstance();
        ArrayList<Laboratory> labs= controller.getLaboratoryController().getLaboratoryArrayList();
        ArrayList<String>labsName=new ArrayList<String>();
        labsName.add("Null");
        for (Laboratory lab: labs) {
            labsName.add(lab.getName());
        }
        labAddEmployee.getItems().addAll(labsName);
    }
}
