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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class ProjectListController implements Initializable {
    @FXML private TableView<Project> ProjectTable;
    @FXML private TableColumn<Project, String> ProjectBudgetTable;
    @FXML private TableColumn<Project, String> ProjectCupTable;
    @FXML private TableColumn<Project, String> ProjectEndDateTable;
    @FXML private TableColumn<Project, String> ProjectNameTable;
    @FXML private TableColumn<Project, String> ProjectRemainingFundsTable;
    @FXML private TableColumn<Project, String> ProjectStartDateTable;
    @FXML private TableColumn<Project, String> scientificReferentTable;
    @FXML private TableColumn<Project, String> scientificResponsibleTable;
    @FXML private Button addProjectButton;
    @FXML private Button dismissProjectButton;
    @FXML private Button modifyProjectButton;
    @FXML void AddProject() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/addProject.fxml"));
        scene = new Scene(root);
        Stage stage= new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
    @FXML public void dismissProject() {
        list.remove(getSelectedProjectIndex());
    }
    @FXML public int getSelectedProjectIndex() {
        return ProjectTable.getSelectionModel().getSelectedIndex();
    }
    @FXML void modifyProject() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/modifyProject.fxml"));
        root=loader.load();
        stage= new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        modifyProjectController controller= loader.getController();
        controller.setProjectIndex(getSelectedProjectIndex());
        stage.showAndWait();
        ProjectTable.refresh();
    }
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML void switchToHomeScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/Home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
static public ObservableList<Project> list= FXCollections.observableArrayList();
    void addList(Project project) {
        list.add(project);
    }
    public ObservableList<Project> getProjectsList(){
        return list;
    }
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        ProjectNameTable.setCellValueFactory(new PropertyValueFactory<Project,String>("name"));
        ProjectCupTable.setCellValueFactory(new PropertyValueFactory<Project,String>("cup"));
        ProjectBudgetTable.setCellValueFactory(new PropertyValueFactory<Project,String>("budget"));
        ProjectStartDateTable.setCellValueFactory(new PropertyValueFactory<Project,String>("startDate"));
        ProjectEndDateTable.setCellValueFactory(new PropertyValueFactory<Project,String>("endDate"));
        ProjectRemainingFundsTable.setCellValueFactory(new PropertyValueFactory<Project,String>("remainingFunds"));
        scientificResponsibleTable.setCellValueFactory(new PropertyValueFactory<Project,String>("SrespSSN"));
        scientificReferentTable.setCellValueFactory(new PropertyValueFactory<Project,String>("SrefSSN"));
        ProjectTable.setItems(getProjectsList());
    }
}

