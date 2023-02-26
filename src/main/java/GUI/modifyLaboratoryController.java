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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class modifyLaboratoryController {
    @FXML private ChoiceBox<String> SrespModifyLaboratory;
    @FXML private Button modifyLaboratoryButton;
    @FXML private TextField nameModifyLaboratory;
    @FXML private ChoiceBox<String> projectModifyLaboratory;
    @FXML private TextArea topicModifyLaboratory;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Controller controller;
    private String name;

    /**
     * Metodo che inizializza i campi della finestra.
     * @param name
     * @param topic
     * @param srespSsn
     * @param projectCup
     */
    public void setDefaultFields(String name, String topic, String srespSsn, String projectCup){
        this.name = name;
        controller = Controller.getInstance();

        //Inizializza il campo del responsabile scientifico
        ArrayList<CompanyEmployee>employeeArrayList=controller.getEmployeeController().getEmployeeArrayList();
        for (CompanyEmployee employee:employeeArrayList) {
            if(employee.getRole().equals("Senior"))
                SrespModifyLaboratory.getItems().add(employee.getSSN());
        }
        //Inizializza il campo del progetto
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        projectModifyLaboratory.getItems().add("");
        for (Project project:projectArrayList) {
            projectModifyLaboratory.getItems().add(project.getCup());
        }

        topicModifyLaboratory.setText(topic);
        nameModifyLaboratory.setText(name);
        projectModifyLaboratory.setValue(projectCup);
        SrespModifyLaboratory.setValue(srespSsn);
    }

    /**
     * Questo metodo viene chiamato quando l'utente conferma la modifica del laboratorio e passa i dati inseriti al controller.
     */
    @FXML void modifyLaboratory() throws IOException{
        ArrayList<String> errors = controller.getLaboratoryController().checkLaboratoryModify(name,
                topicModifyLaboratory.getText(),
                SrespModifyLaboratory.getValue(),
                projectModifyLaboratory.getValue());
        if (errors.isEmpty()) {
            controller.getLaboratoryController().modifyLaboratory(name,
                    topicModifyLaboratory.getText(),
                    SrespModifyLaboratory.getValue(),
                    projectModifyLaboratory.getValue());
            Stage stage = (Stage) modifyLaboratoryButton.getScene().getWindow();
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
