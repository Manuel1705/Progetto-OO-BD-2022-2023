package GUI;
import Controller.Controller;
import Model.CompanyEmployee;
import Model.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.time.LocalDate;
import java.util.ArrayList;

public class modifyProjectController {

    @FXML
    private ChoiceBox<String> SrefModifyProject;

    @FXML
    private ChoiceBox<String> SrespModifyProject;

    @FXML
    private TextField budgetModifyProject;

    @FXML
    private TextField cupModifyProject;

    @FXML
    private DatePicker endDateModifyProject;

    @FXML
    private Button modifyProjectButton;

    @FXML
    private TextField nameModifyProject;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Controller controller;

    /**
     * Metodo che inizializza i campi della finestra.
     * @param cup       Codice identificativo del progetto
     * @param name      Nome del progetto
     * @param budget    Soldi stanziati per il progetto
     * @param sResp     SSN del responsabile scientifico
     * @param sRef      SSN del referente scientifico
     * @param endDate   Data di scadenza del progetto
     */
    public void setDefaultFields(String cup, String name, String budget, String sResp, String sRef,
                                 String endDate)
    {
        controller=Controller.getInstance();
        ArrayList<CompanyEmployee> employeeArrayList=controller.getEmployeeController().getEmployeeArrayList();
        for (CompanyEmployee employee: employeeArrayList) {
            if(employee.getRole().equals("Executive")){
                SrespModifyProject.getItems().add(employee.getSSN());
            }
            else if(employee.getRole().equals("Senior")) {
                SrefModifyProject.getItems().add(employee.getSSN());
            }
        }
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        cupModifyProject.setText(cup);
        nameModifyProject.setText(name);
        budgetModifyProject.setText(budget);
        endDateModifyProject.setValue(LocalDate.parse(endDate));
        SrefModifyProject.setValue(sRef);
        SrespModifyProject.setValue(sResp);
    }

    /**
     * Metodo che viene chiamato quando l'utente conferma la modifica del progetto.
     * Verifica la validità dell'input e passa i dati al controller.
     * @throws IOException Gestione delle eccezioni di I/O
     */
    @FXML
    public void modifyProject() throws IOException{
       ArrayList<String> errors = new ArrayList<String>();
        try{
            Float.parseFloat(budgetModifyProject.getText());
        }
        catch (Exception ex){
            errors.add("Budget must be a valid number.");
            budgetModifyProject.setText("0");
        }
        errors.addAll(controller.getProjectController().checkProjectModify(cupModifyProject.getText(),
                nameModifyProject.getText(),
                Float.parseFloat(budgetModifyProject.getText()),
                endDateModifyProject.getValue(),
                SrefModifyProject.getValue(),
                SrespModifyProject.getValue()));

        if (errors.isEmpty()) {
            controller.getProjectController().modifyProjectList(cupModifyProject.getText(),
                    nameModifyProject.getText(),
                    Float.parseFloat(budgetModifyProject.getText()),
                    endDateModifyProject.getValue(),
                    SrefModifyProject.getValue(),
                    SrespModifyProject.getValue());
            Stage stage = (Stage) modifyProjectButton.getScene().getWindow();
            stage.close();
        }
        else{
            showErrorWindow(errors);
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
