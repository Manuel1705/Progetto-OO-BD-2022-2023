package GUI;
import Model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Controller.Controller;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class ProjectListController implements Initializable {
    @FXML private TableView<Project> ProjectTable;
    @FXML private TableColumn<Project, Float> ProjectBudgetTable;
    @FXML private TableColumn<Project, String> ProjectCupTable;
    @FXML private TableColumn<Project, LocalDate> ProjectEndDateTable;
    @FXML private TableColumn<Project, String> ProjectNameTable;
    @FXML private TableColumn<Project, Float> ProjectRemainingFundsTable;
    @FXML private TableColumn<Project, LocalDate> ProjectStartDateTable;
    @FXML private TableColumn<Project, String> scientificReferentTable;
    @FXML private TableColumn<Project, String> scientificResponsibleTable;
    @FXML private Button addProjectButton;
    @FXML private Button dismissProjectButton;
    @FXML private Button modifyProjectButton;

    private ObservableList<Project> list= FXCollections.observableArrayList();
    private Controller controller;

    /**
     * Metodo che carica i progetti salvati nel controller nell'Observable List
     */
    private void loadList(){
        list.clear();
        list.addAll(controller.getProjectController().getProjectArrayList());
    }

    /**
     * Metodo che apre la finestra per inserire un progetto.
     * @throws IOException
     */
    @FXML public void AddProject() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/addProject.fxml"));
        scene = new Scene(root);
        Stage stage= new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.getIcons().add(new Image("app-icon.png"));
        stage.showAndWait();
        loadList();
    }

    /**
     * Metodo che rimuove il progetto selezionato dall'utente.
     */
    @FXML public void dismissProject() throws IOException{
        if(ProjectCupTable.getCellObservableValue(getSelectedProjectIndex()) != null) {
            String selectedCUP = ProjectCupTable.getCellObservableValue(getSelectedProjectIndex()).getValue();
            ArrayList<String> errors = controller.getProjectController().checkProjectDelete(selectedCUP);
            if (errors.isEmpty())
                controller.getProjectController().dismissProject(selectedCUP);
            else
                showErrorWindow(errors);
            loadList();
        }
    }

    /**
     * Metodo che restituisce l'indice del progetto selezionato dall'utente nella tabella.
     * @return
     */
    @FXML public int getSelectedProjectIndex() {
        return ProjectTable.getSelectionModel().getSelectedIndex();
    }

    /**
     * Metodo che apre la finestra per modificare il progetto selezionato dall'utente.
     * @throws IOException
     */
    @FXML public void modifyProject() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/modifyProject.fxml"));
        root=loader.load();
        stage= new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        modifyProjectController controller= loader.getController();
        int i = getSelectedProjectIndex();
        if(ProjectCupTable.getCellObservableValue(i) != null) {
            controller.setDefaultFields(ProjectCupTable.getCellObservableValue(i).getValue(),
                    ProjectNameTable.getCellObservableValue(i).getValue(),
                    ProjectBudgetTable.getCellObservableValue(i).getValue().toString(),
                    scientificResponsibleTable.getCellObservableValue(i).getValue(),
                    scientificReferentTable.getCellObservableValue(i).getValue(),
                    ProjectEndDateTable.getCellObservableValue(i).getValue().toString());
            stage.getIcons().add(new Image("app-icon.png"));
            stage.showAndWait();
            loadList();
        }
    }
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Metodo che ritorna alla schermata iniziale
     * @param event
     * @throws IOException
     */
    @FXML public void switchToHomeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../GUI/Home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
       // stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo che restituisce la lista della tabella.
     * @return
     */
    private ObservableList<Project> getProjectsList(){
        return list;
    }

    /**
     * Metodo che inizializza la finestra
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        //Viene inizializzato il controller
        controller=Controller.getInstance();
        //Vengono caricati i dati salvati nel controller
        loadList();
        //Vengono inizializzate le celle della tabella
        ProjectNameTable.setCellValueFactory(new PropertyValueFactory<Project,String>("name"));
        ProjectCupTable.setCellValueFactory(new PropertyValueFactory<Project,String>("cup"));
        ProjectBudgetTable.setCellValueFactory(new PropertyValueFactory<Project,Float>("budget"));
        ProjectStartDateTable.setCellValueFactory(new PropertyValueFactory<Project,LocalDate>("startDate"));
        ProjectEndDateTable.setCellValueFactory(new PropertyValueFactory<Project,LocalDate>("endDate"));
        ProjectRemainingFundsTable.setCellValueFactory(new PropertyValueFactory<Project,Float>("remainingFunds"));
        scientificResponsibleTable.setCellValueFactory(new PropertyValueFactory<Project,String>("SrespSSN"));
        scientificReferentTable.setCellValueFactory(new PropertyValueFactory<Project,String>("SrefSSN"));
        ProjectTable.setItems(getProjectsList());
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

