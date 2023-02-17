package GUI;
import Controller.Controller;
import Model.Employee;
import Model.Project;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class addLaboratoryController implements Initializable {
    @FXML private ChoiceBox<String> SrespAddLaboratory;
    @FXML private Button addLaboratoryButton;
    @FXML private TextField nameAddLaboratory;
    @FXML private ChoiceBox<String> projectAddLaboratory;
    @FXML private TextArea topicAddLaboratory;
    private Controller controller;

    /**
     * Metodo che viene chiamato quando l'utente conferma l'inserimento dei dati. Effettua controlli sulla validita' dell'input
     * e tramite il controller inserisce i dati relativi al laboratorio.
     */
    @FXML void addLaboratory() {
        boolean check = true;
        if (!nameAddLaboratory.getText().isBlank() && !SrespAddLaboratory.getValue().isBlank()) {
            //Controllo sulla lunghezza del nome
            if (nameAddLaboratory.getText().length() > 30) {
                check = false;
            }
            //Controllo sulla lunghezza del topic.
            if (topicAddLaboratory.getText().length() > 50) {
                check = false;
            }

            if (check) {
                controller.getLaboratoryController().addLaboratoryList(
                    nameAddLaboratory.getText(),
                    topicAddLaboratory.getText(),
                    SrespAddLaboratory.getValue(),
                    projectAddLaboratory.getValue());

                Stage stage = (Stage) addLaboratoryButton.getScene().getWindow();
                stage.close();
            }
        }
    }

    /**
     * Metodo che inizializza la finestra.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = Controller.getInstance();
        ArrayList<Employee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();

        //Inizializza il menu a tendina dei possibili responsabili scientifici
        ArrayList<String> Sresp= new ArrayList<>();
        for (Employee employee: employeeArrayList) {
            if(employee.getRole().equals("Senior")) {
                Sresp.add(employee.getSSN());
            }
        }
        SrespAddLaboratory.getItems().addAll(Sresp);

        //Inizializza il menu a tendina dei potenziali progetti
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        ArrayList<String>projects= new ArrayList<>();
        projects.add("Null");
        for (Project project: projectArrayList) {
            projects.add(project.getCup());
        }
        projectAddLaboratory.getItems().addAll(projects);
    }
}

