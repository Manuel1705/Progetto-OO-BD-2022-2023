package GUI;
import Controller.Controller;
import Model.Laboratory;
import Model.TemporaryEmployee;
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
    private TextField projectModifyTemporaryEmployee;

    @FXML
    private TextField salaryModifyTemporaryEmployee;

    @FXML
    private TextField ssnModifyTemporaryEmployee;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Controller controller;

    /**
     * Metodo che inizializza i campi della finestra con i dati passati in input.
     * @param ssn           Social Security Number dell'impiegato temporaneo
     * @param firstName     Nome dell'impiegato temporaneo
     * @param lastName      Cognome dell'impiegato temporaneo
     * @param phoneNumber   Recapito telefonico dell'impiegato temporaneo
     * @param address       Indirizzo dell'impiegato temporaneo
     * @param project       Progetto per il quale l'impiegato temporaneo lavora
     * @param labName       Nome del laboratorio presso cui l'impiegato temporaneo lavora
     * @param salary        Salario dell'impiegato temporaneo
     * @param email         Email dell'impiegato temporaneo
     */
    public void setDefaultFields(String ssn, String firstName, String lastName, String phoneNumber, String address,
                                 String project, String labName, String salary, String email)
    {
    controller=Controller.getInstance();
    ArrayList<Laboratory> labs = controller.getLaboratoryController().getLaboratoryArrayList();
    //Inizializzazione menu a tendina laboratori
    labModifyTemporaryEmployee.getItems().add("");
    for (Laboratory lab: labs){
        labModifyTemporaryEmployee.getItems().add(lab.getName());
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
        projectModifyTemporaryEmployee.setText(project);;
}
    /**
     * Metodo che effettua i controlli sulla validità dell'input e passa i dati al controller che modifica i dati dell'impiegato.
     * @throws IOException Gestione delle eccezioni di I/O
     */
    @FXML
    public void modifyTemporaryEmployee() throws IOException {
        ArrayList<String> errors = new ArrayList<String>();

        try{
            Float.parseFloat(salaryModifyTemporaryEmployee.getText());
        }
        catch (Exception ex){
            errors.add("Salary must be a valid number.");
            salaryModifyTemporaryEmployee.setText("0");
        }

        errors.addAll(controller.getTemporaryEmployeeController().checkTemporaryEmployeeModify(ssnModifyTemporaryEmployee.getText(),
                firstNameModifyTemporaryEmployee.getText(),
                lastNameModifyTemporaryEmployee.getText(),
                phoneNumberModifyTemporaryEmployee.getText(),
                Float.parseFloat(salaryModifyTemporaryEmployee.getText()),
                labModifyTemporaryEmployee.getValue(),
                addressModifyTemporaryEmployee.getText(),
                emailModifyTemporaryEmployee.getText(),
                projectModifyTemporaryEmployee.getText()));

        if (errors.isEmpty()) {
            controller.getTemporaryEmployeeController().modifyTemporaryEmployeeList(ssnModifyTemporaryEmployee.getText(),
                    firstNameModifyTemporaryEmployee.getText(),
                    lastNameModifyTemporaryEmployee.getText(),
                    phoneNumberModifyTemporaryEmployee.getText(),
                    Float.parseFloat(salaryModifyTemporaryEmployee.getText()),
                    labModifyTemporaryEmployee.getValue(),
                    addressModifyTemporaryEmployee.getText(),
                    emailModifyTemporaryEmployee.getText(),
                    projectModifyTemporaryEmployee.getText());
            Stage stage = (Stage) modifyTemporaryEmployeeButton.getScene().getWindow();
            stage.close();
        }
        else
            showErrorWindow(errors);
    }

    /**
     * Metodo che apre una finestra elencando gli errori passati in input.
     * @param errors Stringhe di errori rilevati
     * @throws IOException Gestione delle eccezioni di I/O
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
