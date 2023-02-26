package GUI;
import Controller.Controller;
import Model.CompanyEmployee;
import Model.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class addLaboratoryController implements Initializable {
    @FXML private ChoiceBox<String> SrespAddLaboratory;
    @FXML private Button addLaboratoryButton;
    @FXML private TextField nameAddLaboratory;
    @FXML private ChoiceBox<String> projectAddLaboratory;
    @FXML private TextArea topicAddLaboratory;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Controller controller;

    /**
     * Metodo che viene chiamato quando l'utente conferma l'inserimento dei dati. Effettua controlli sulla validita' dell'input
     * e tramite il controller inserisce i dati relativi al laboratorio.
     */
    @FXML void addLaboratory() throws IOException{
        ArrayList<String> errors = controller.getLaboratoryController().checkLaboratoryInsert(nameAddLaboratory.getText(),
                topicAddLaboratory.getText(),
                SrespAddLaboratory.getValue(),
                projectAddLaboratory.getValue());

        if (errors.isEmpty()) {
                controller.getLaboratoryController().addLaboratoryList(
                    nameAddLaboratory.getText(),
                    topicAddLaboratory.getText(),
                    SrespAddLaboratory.getValue(),
                    projectAddLaboratory.getValue());

                Stage stage = (Stage) addLaboratoryButton.getScene().getWindow();
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = Controller.getInstance();
        ArrayList<CompanyEmployee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();

        //Inizializza il menu a tendina dei possibili responsabili scientifici
        ArrayList<String> Sresp= new ArrayList<>();
        for (CompanyEmployee employee: employeeArrayList) {
            if(employee.getRole().equals("Senior")) {
                Sresp.add(employee.getSSN());
            }
        }
        SrespAddLaboratory.getItems().addAll(Sresp);

        //Inizializza il menu a tendina dei potenziali progetti
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        ArrayList<String>projects= new ArrayList<>();
        projects.add("");
        for (Project project: projectArrayList) {
            projects.add(project.getCup());
        }
        projectAddLaboratory.getItems().addAll(projects);
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

