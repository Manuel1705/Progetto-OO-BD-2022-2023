package GUI;
import Controller.Controller;
import Model.Employee;
import Model.Laboratory;
import Model.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class modifyLaboratoryController {
    @FXML private ChoiceBox<String> SrespModifyLaboratory;
    @FXML private Button modifyLaboratoryButton;
    @FXML private TextField nameModifyLaboratory;
    @FXML private ChoiceBox<String> projectModifyLaboratory;
    @FXML private TextArea topicModifyLaboratory;
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
        ArrayList<Employee>employeeArrayList=controller.getEmployeeController().getEmployeeArrayList();
        for (Employee employee:employeeArrayList) {
            if(employee.getRole().equals("Senior"))
                SrespModifyLaboratory.getItems().add(employee.getSSN());
        }
        //Inizializza il campo del progetto
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        projectModifyLaboratory.getItems().add("Null");
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
    @FXML void modifyLaboratory() {
        boolean check = true;
        if (!nameModifyLaboratory.getText().isBlank() && !SrespModifyLaboratory.getValue().isBlank()) {
            //Controllo sulla lunghezza del nome
            if (nameModifyLaboratory.getText().length() > 30) {
                check = false;
            }
            //Controllo sulla lunghezza del topic.
            if (topicModifyLaboratory.getText().length() > 50) {
                check = false;
            }
            if (check) {
                controller.getLaboratoryController().modifyLaboratory(name,
                        topicModifyLaboratory.getText(),
                        SrespModifyLaboratory.getValue(),
                        projectModifyLaboratory.getValue());
                Stage stage = (Stage) modifyLaboratoryButton.getScene().getWindow();
                stage.close();
            }
        }
    }
}
