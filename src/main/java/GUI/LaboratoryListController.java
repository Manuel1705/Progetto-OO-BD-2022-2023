
package GUI;
import Model.Laboratory;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class LaboratoryListController implements Initializable {
        @FXML private TableView<Laboratory> LabTable;
        @FXML private TableColumn<Laboratory, String> LabNameTable;
        @FXML private TableColumn<Laboratory, String> LabTopicTable;
        @FXML private TableColumn<Laboratory, String> LabProjectTable;
        @FXML private TableColumn<Laboratory, String> SrespLabTable;
        @FXML private Button addLabButton;
        @FXML private Button dismissLabButton;
        @FXML private Button modifyLabButton;

        static public ObservableList<Laboratory> list = FXCollections.observableArrayList();
        public ObservableList<Laboratory> getLaboratoryList(){
            return list;
        }
        public void AddLaboratoryList(Laboratory lab) {
            list.add(lab);
        }
    @FXML void AddLaboratory() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/addLaboratory.fxml"));
        scene = new Scene(root);
        stage= new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
        @FXML void modifyLaboratory() {
        }
        @FXML void dismissLaboratory() {
                list.remove(getSelectedLabIndex());
        }
        @Override public void initialize(URL url, ResourceBundle resourceBundle) {
            LabNameTable.setCellValueFactory(new PropertyValueFactory<Laboratory,String>("name"));
            LabTopicTable.setCellValueFactory(new PropertyValueFactory<Laboratory,String>("topic"));
            LabProjectTable.setCellValueFactory(new PropertyValueFactory<Laboratory,String>("ProjectCup"));
            SrespLabTable.setCellValueFactory(new PropertyValueFactory<Laboratory,String>("SrespSSN"));
            LabTable.setItems(getLaboratoryList());
    }
    @FXML public int getSelectedLabIndex() {
                return LabTable.getSelectionModel().getSelectedIndex();
        }
        private Stage stage;
        private Scene scene;
        private Parent root;
    @FXML void switchToHomeScene(ActionEvent event)  throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("../GUI/Home.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }

}
