package GUI;
import Controller.Controller;
import Model.Laboratory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    @FXML private DatePicker dateAddEmployee;
    @FXML private TextField salaryAddEmployee;
    @FXML private TextField ssnAddEmployee;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Controller controller;

    /**
     * Metodo che viene chiamato quando l'utente conferma l'inserimento.
     * Esegue i controlli sulla validita' dell'input e passa i dati inseriti dall'utente al controller che crea l'oggetto impiegato.
     */
    @FXML void hireEmployee() throws IOException{
        ArrayList<String> errors = new ArrayList<String>();

        try{
            Float.parseFloat(salaryAddEmployee.getText());
        }
        catch (Exception ex){
            errors.add("Salary must be a valid number.");
            salaryAddEmployee.setText("0");
        }

        errors.addAll(controller.getEmployeeController().checkEmployeeInsert(ssnAddEmployee.getText(),
                firstNameAddEmployee.getText(),
                lastNameAddEmployee.getText(),
                phoneNumberAddEmployee.getText(),
                addressAddEmployee.getText(),
                emailAddEmployee.getText(),
                dateAddEmployee.getValue(),
                Float.parseFloat(salaryAddEmployee.getText()),
                labAddEmployee.getValue()));


        //Se tutti i controlli sono risultati validi il metodo passa i dati al controller.
        if (errors.isEmpty()) {
            controller.getEmployeeController().addEmployeeList(
                    ssnAddEmployee.getText(),
                    firstNameAddEmployee.getText(),
                    lastNameAddEmployee.getText(),
                    phoneNumberAddEmployee.getText(),
                    addressAddEmployee.getText(),
                    emailAddEmployee.getText(),
                    dateAddEmployee.getValue(),
                    Float.parseFloat(salaryAddEmployee.getText()),
                    labAddEmployee.getValue());
            //chiusura finestra pop up
            Stage stage = (Stage) hireEmployeeButton.getScene().getWindow();
            stage.close();
            }
        //Altrimenti viene mostrata una finestra di errore
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

    /**
     * Metodo che inizializza la finestra
     */

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] roles={"Junior","Executive"};
        controller=Controller.getInstance();
        ArrayList<Laboratory> labs= controller.getLaboratoryController().getLaboratoryArrayList();
        labAddEmployee.getItems().add(null);
        for (Laboratory lab: labs) {
            labAddEmployee.getItems().add(lab.getName());
        }
    }
}
