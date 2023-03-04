package GUI;
import Controller.Controller;
import Model.Laboratory;
import Model.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class addTemporaryEmployeeController implements Initializable {

    @FXML
    private TextField addressAddTemporaryEmployee;

    @FXML
    private TextField emailAddTemporaryEmployee;

    @FXML
    private TextField firstNameAddTemporaryEmployee;

    @FXML
    private Button hireTemporaryEmployeeButton;

    @FXML
    private ChoiceBox<String> labAddTemporaryEmployee;

    @FXML
    private TextField lastNameAddTemporaryEmployee;

    @FXML
    private TextField phoneNumberAddTemporaryEmployee;

    @FXML
    private ChoiceBox<String> projectAddTemporaryEmployee;

    @FXML
    private TextField salaryAddTemporaryEmployee;

    @FXML
    private TextField ssnAddTemporaryEmployee;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Controller controller;

    /**
     * Metodo che viene chiamato quando l'utente conferma l'inserimento dei dati forniti. Effettua i controlli sulla
     * validità dell'input.
     * @throws IOException Gestione delle eccezioni di I/O
     */
    @FXML
    public void hireTemporaryEmployee() throws IOException {
        ArrayList<String> errors = new ArrayList<String>();

        try{
            Float.parseFloat(salaryAddTemporaryEmployee.getText());
        }
        catch (Exception ex){
            errors.add("Salary must be a valid number.");
            salaryAddTemporaryEmployee.setText("0");
        }

        errors.addAll(controller.getTemporaryEmployeeController().checkTemporaryEmployeeInsert(ssnAddTemporaryEmployee.getText(),
                firstNameAddTemporaryEmployee.getText(),
                lastNameAddTemporaryEmployee.getText(),
                phoneNumberAddTemporaryEmployee.getText(),
                addressAddTemporaryEmployee.getText(),
                emailAddTemporaryEmployee.getText(),
                LocalDate.now(),
                Float.parseFloat(salaryAddTemporaryEmployee.getText()),
                labAddTemporaryEmployee.getValue(),
                projectAddTemporaryEmployee.getValue()));

        if (errors.isEmpty()) {
            controller.getTemporaryEmployeeController().addTemporaryEmployeeList(ssnAddTemporaryEmployee.getText(),
                    firstNameAddTemporaryEmployee.getText(),
                    lastNameAddTemporaryEmployee.getText(),
                    phoneNumberAddTemporaryEmployee.getText(),
                    addressAddTemporaryEmployee.getText(),
                    emailAddTemporaryEmployee.getText(),
                    LocalDate.now(),
                    Float.parseFloat(salaryAddTemporaryEmployee.getText()),
                    labAddTemporaryEmployee.getValue(),
                    projectAddTemporaryEmployee.getValue());

            Stage stage = (Stage) hireTemporaryEmployeeButton.getScene().getWindow();
            stage.close();
        }
        else{
            showErrorWindow(errors);
        }
    }


    /**
     * Metodo che inizializza la finestra.
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = Controller.getInstance();
        ArrayList<Laboratory> labs= controller.getLaboratoryController().getLaboratoryArrayList();
        labAddTemporaryEmployee.getItems().add("");
        for (Laboratory lab: labs) {
            labAddTemporaryEmployee.getItems().add(lab.getName());
        }
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        for (Project prj:projectArrayList) {
            projectAddTemporaryEmployee.getItems().add(prj.getCup());
        }
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

