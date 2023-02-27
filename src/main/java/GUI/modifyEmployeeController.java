package GUI;
import Model.Laboratory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Controller controller;


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
        labModifyEmployee.getItems().add(null);
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
    @FXML void modifyEmployee() throws IOException {
        ArrayList<String> errors = new ArrayList<String>();

        try{
            Float.parseFloat(salaryModifyEmployee.getText());
        }
        catch (Exception ex){
            errors.add("Salary must be a valid number.");
        }

        errors.addAll(controller.getEmployeeController().checkEmployeeModify(ssnModifyEmployee.getText(),
                firstNameModifyEmployee.getText(),
                lastNameModifyEmployee.getText(),
                phoneNumberModifyEmployee.getText(),
                addressModifyEmployee.getText(),
                roleModifyEmployee.getValue(),
                emailModifyEmployee.getText(),
                Float.parseFloat(salaryModifyEmployee.getText()),
                labModifyEmployee.getValue()));


        //Chiamata a controller
        if (errors.isEmpty()) {
            controller.getEmployeeController().modifyEmployeeList(ssnModifyEmployee.getText(),
                    firstNameModifyEmployee.getText(),
                    lastNameModifyEmployee.getText(),
                    phoneNumberModifyEmployee.getText(),
                    roleModifyEmployee.getValue(),
                    Float.parseFloat(salaryModifyEmployee.getText()),
                    labModifyEmployee.getValue(),
                    addressModifyEmployee.getText(),
                    emailModifyEmployee.getText());
            //Chiusura finestra
            Stage stage = (Stage) modifyEmployeeButton.getScene().getWindow();
            stage.close();

            }
        else{
            showErrorWindow(errors);
        }
    }

    /**
     * Metodo che apre una finestra elencando gli errori passati in input.
     * @param errors
     * @throws IOException
     */
    private void showErrorWindow (ArrayList<String> errors) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/ErrorWindow.fxml"));
        root=loader.load();
        stage= new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.getIcons().add(new Image("app-icon.png"));
        ErrorWindowController errorWindow = loader.getController();
        errorWindow.setErrors(errors);
        stage.showAndWait();
    }
}
