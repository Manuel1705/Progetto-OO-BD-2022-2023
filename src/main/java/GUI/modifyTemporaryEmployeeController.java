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
    String ssn;
    public void setDefaultFields(String ssn, String firstName, String lastName, String phoneNumber, String address,
                                 String project, String labName, String salary, String email)
    {
    controller=Controller.getInstance();
    this.ssn = ssn;
    ArrayList<Laboratory> labs = controller.getLaboratoryController().getLaboratoryArrayList();
    //Inizializzazione menu a tendina laboratori
    labModifyTemporaryEmployee.getItems().add("Null");
    for (Laboratory lab: labs){
        labModifyTemporaryEmployee.getItems().add(lab.getName());
    }
    //Inizializzazione menu a tendina progetti
    ArrayList<Project> projects= controller.getProjectController().getProjectArrayList();
    for (Project prj: projects){
        projectModifyTemporaryEmployee.getItems().add(prj.getCup());
    }
    ArrayList<TemporaryEmployee>employeeArrayList=controller.getTemporaryEmployeeController().getTemporaryEmployeeArrayList();
    firstNameModifyTemporaryEmployee.setText(firstName);
    lastNameModifyTemporaryEmployee.setText(lastName);
    phoneNumberModifyTemporaryEmployee.setText(phoneNumber);
    addressModifyTemporaryEmployee.setText(address);
    labModifyTemporaryEmployee.setValue(labName);
    salaryModifyTemporaryEmployee.setText(salary);
    ssnModifyTemporaryEmployee.setText(ssn);
    emailModifyTemporaryEmployee.setText(email);
    projectModifyTemporaryEmployee.setValue(project);
}
    /**
     * Metodo che effettua i controlli sulla validita' dell'input e passa i dati al controller che modifica i dati dell'impiegato.
     */
    @FXML
    void modifyTemporaryEmployee() {
        boolean check = true;
        //Controllo sul dominio del SSN
        if (ssnModifyTemporaryEmployee.getText().length() != 15) {
            check = false;
        } else {
            for (char c : ssnModifyTemporaryEmployee.getText().toCharArray()) {
                if (!Character.isDigit(c)) {
                    check = false;
                    break;
                }
            }
        }
        //Controllo lunghezza nome
        if(firstNameModifyTemporaryEmployee.getText().length()>30){
            check=false;
        }
        //Controllo lunghezza cognome
        if(lastNameModifyTemporaryEmployee.getText().length()>30){
            check=false;
        }
        //Controllo lunghezza numero telefonico
        if (phoneNumberModifyTemporaryEmployee.getText().length() != 10) {
            check = false;
        }

        if (check) {
            controller.getTemporaryEmployeeController().modifyTemporaryEmployeeList(ssn,
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
}
