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
    private Controller controller;

    /**
     * Metodo che viene chiamato quando l'utente conferma l'inserimento.
     * Esegue i controlli sulla validita' dell'input e passa i dati inseriti dall'utente al controller che crea l'oggetto impiegato.
     */
    @FXML void hireEmployee(){

        //Controllo sulla validita' dei campi obbligatori.
        if(!roleAddEmployee.getValue().isBlank() &&
                !ssnAddEmployee.getText().isBlank() &&
                !firstNameAddEmployee.getText().isBlank() &&
                !lastNameAddEmployee.getText().isBlank()&&
                !salaryAddEmployee.getText().isBlank() &&
                !phoneNumberAddEmployee.getText().isBlank())
        {
            boolean check = true;
            //Controllo sul dominio dell'SSN
            if (ssnAddEmployee.getText().length() != 15) {
                check = false;
            } else {
                for (char c : ssnAddEmployee.getText().toCharArray()) {
                    if (!Character.isDigit(c)) {
                        check = false;
                        break;
                    }
                }
            }
            //Controllo sulla lunghezza del nome
            if(firstNameAddEmployee.getText().length()>30){
                check=false;
            }
            //Controllo sulla lunghezza del cognome
            if (lastNameAddEmployee.getText().length()>30){
                check=false;
            }
            //Controllo sul dominio del numero di telefono
            if (phoneNumberAddEmployee.getText().length() != 10) {
                check = false;
            } else {
                for (char c : phoneNumberAddEmployee.getText().toCharArray()) {
                    if (!Character.isDigit(c)) {
                        check = false;
                        break;
                    }
                }
            }
           //Se tutti i controlli sono risultati validi il metodo passa i dati al controller.
            if (check) {
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
    }

    /**
     * Metodo che inizializza la finestra
     */

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] roles={"Junior","Executive"};
        roleAddEmployee.getItems().addAll(roles);
        controller=Controller.getInstance();
        ArrayList<Laboratory> labs= controller.getLaboratoryController().getLaboratoryArrayList();
        labAddEmployee.getItems().add("Null");
        for (Laboratory lab: labs) {
            labAddEmployee.getItems().add(lab.getName());
        }
    }
}
