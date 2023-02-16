package GUI;
import Model.Laboratory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Period;
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

    private String ssn;

    /**
     * Inizializza i campi della finestra con i dati passati in input.
     * @param ssn SSN dell'impiegato selezionato.
     * @param firstName Nome dell'impiegato.
     * @param lastName Cognome dell'impiegato.
     * @param phoneNumber Numero di telefono dell'impiegato.
     * @param address Indirizzo dell'impiegato.
     * @param role Ruolo dell'impiegato.
     * @param labName Nome del laboratorio dell'impiegato.
     * @param salary Salario dell'impiegato.
     * @param email Email dell'impiegato.
     * @param employmentDate Data di assunzione dell'impiegato.
     */
    public void setDefaultFields(String ssn, String firstName, String lastName, String phoneNumber, String address,
                                 String role, String labName, String salary, String email, String employmentDate)
    {

        this.ssn = ssn;
        controller=Controller.getInstance();

        //Inizializzazione menu a tendina per il ruolo dell'impiegato.
        String[] roles = {"","Executive"};
        int employmentTime = Period.between(LocalDate.parse(employmentDate), LocalDate.now()).getYears();

        if(employmentTime < 3)  roles[0] = "Junior";
        else if(employmentTime >= 3 && employmentTime < 7) roles [0] = "Middle";
        else roles[0] = "Senior";
        roleModifyEmployee.getItems().addAll(roles);

        //Inizializzazione menu a tendina per i laboratori.
        ArrayList<Laboratory> labs= controller.getLaboratoryController().getLaboratoryArrayList();
        labModifyEmployee.getItems().add("Null");
        for (Laboratory lab: labs){
            labModifyEmployee.getItems().add(lab.getName());
        }

        //Inizializzazione campi
        firstNameModifyEmployee.setText(firstName);
        lastNameModifyEmployee.setText(lastName);
        phoneNumberModifyEmployee.setText(phoneNumber);
        addressModifyEmployee.setText(address);
        roleModifyEmployee.setValue(role);
        labModifyEmployee.setValue(labName);
        salaryModifyEmployee.setText(salary);
        ssnModifyEmployee.setText(ssn);
        emailModifyEmployee.setText(email);
    }

    /**
     * Metodo che effettua i controlli sulla validita' dell'input e passa i dati al controller che modifica i dati dell'impiegato.
     */
    @FXML void modifyEmployee() {
        boolean check = true;
        //SSN non puo' essere modificato
        /*if (ssnModifyEmployee.getText().length() != 15) {
            check = false;
        } else {
            for (char c : ssnModifyEmployee.getText().toCharArray()) {
                if (!Character.isDigit(c)) {
                    check = false;
                    break;
                }
            }
        }*/
        //Controllo lunghezza nome
        if(firstNameModifyEmployee.getText().length()>30){
            check=false;
        }
        //Controllo lunghezza cognome
        if(lastNameModifyEmployee.getText().length()>30){
            check=false;
        }
        //Controllo dominio numero telefonico
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
        //Chiamata a controller
        if (check) {
            controller.getEmployeeController().modifyEmployeeList(ssn,
                    firstNameModifyEmployee.getText(),
                    lastNameModifyEmployee.getText(),
                    phoneNumberModifyEmployee.getText(),
                    roleModifyEmployee.getValue(),
                    salaryModifyEmployee.getText(),
                    labModifyEmployee.getValue(),
                    addressModifyEmployee.getText(),
                    emailModifyEmployee.getText());

            }
        //Chiusura finestra
        Stage stage = (Stage) modifyEmployeeButton.getScene().getWindow();
        stage.close();

    }
}
