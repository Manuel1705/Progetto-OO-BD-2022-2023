
package GUI;
import Model.Laboratory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import Controller.Controller;
public class LaboratoryListController implements Initializable {
    @FXML private TableView<Laboratory> LabTable;
    @FXML private TableColumn<Laboratory, String> LabNameTable;
    @FXML private TableColumn<Laboratory, String> LabTopicTable;
    @FXML private TableColumn<Laboratory, String> LabProjectTable;
    @FXML private TableColumn<Laboratory, String> SrespLabTable;
    @FXML private Button addLabButton;
    @FXML private Button dismissLabButton;
    @FXML private Button modifyLabButton;
    private Controller controller;

    private ObservableList<Laboratory> list = FXCollections.observableArrayList();

    /**
     * Metodo che carica i dati dal controller.
     */
    public void loadList(){
            list.clear();
            list.addAll(controller.getLaboratoryController().getLaboratoryArrayList());
    }

    /**
     * Metodo che restituisce list.
     * @return lista di laboratori
     */
    private ObservableList<Laboratory> getLaboratoryList(){
            return list;
    }

    /**
     * Metodo che apre la finestra per aggiungere un nuovo laboratorio.
     * @throws IOException gestione delle eccezioni di I/O
     */
    @FXML public void AddLaboratory() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/addLaboratory.fxml"));
        scene = new Scene(root);
        stage= new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.getIcons().add(new Image("app-icon.png"));
        stage.showAndWait();
        loadList();
    }

    /**
     * Metodo che apre la finestra per modificare il laboratorio selezionato dall'utente.
     * @throws IOException Gestione delle eccezioni di I/O
     */
    @FXML
    public void modifyLaboratory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/modifyLaboratory.fxml"));
        root = loader.load();
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        modifyLaboratoryController controller = loader.getController();
        int i = getSelectedLabIndex();
        if(LabNameTable.getCellObservableValue(i) != null) {
            controller.setDefaultFields(LabNameTable.getCellObservableValue(i).getValue(),
                    LabTopicTable.getCellObservableValue(i).getValue(),
                    SrespLabTable.getCellObservableValue(i).getValue(),
                    LabProjectTable.getCellObservableValue(i).getValue());
            stage.getIcons().add(new Image("app-icon.png"));
            stage.showAndWait();
            loadList();
        }
    }

    /**
     * Metodo che rimuove il laboratorio selezionato dall'utente.
     */
    @FXML
    public void dismissLaboratory() throws IOException{
        if(LabNameTable.getCellObservableValue(getSelectedLabIndex()) != null) {
            String selectedName = LabNameTable.getCellObservableValue(getSelectedLabIndex()).getValue();
            ArrayList<String> errors = controller.getLaboratoryController().checkLaboratoryDelete(selectedName);
            if (errors.isEmpty())
                controller.getLaboratoryController().dismissLaboratory(selectedName);
            else
                showErrorWindow(errors);
            loadList();
        }
    }

    /**
     * Metodo che inizializza la tabella.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Il metodo ottiene il controller
        controller = Controller.getInstance();
        //Il metodo carica i dati salvati nel controller
        loadList();
        //Vengono inizializzate le righe della tabella
        LabNameTable.setCellValueFactory(new PropertyValueFactory<Laboratory, String>("name"));
        LabTopicTable.setCellValueFactory(new PropertyValueFactory<Laboratory, String>("topic"));
        LabProjectTable.setCellValueFactory(new PropertyValueFactory<Laboratory, String>("ProjectCup"));
        SrespLabTable.setCellValueFactory(new PropertyValueFactory<Laboratory, String>("SrespSSN"));
        LabTable.setItems(getLaboratoryList());
    }

    /**
     * Metodo che restituisce l'indice del laboratorio selezionato nella tabella.
     * @return L'indice del laboratorio
     */
    @FXML public int getSelectedLabIndex() {
        return LabTable.getSelectionModel().getSelectedIndex();
    }
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Metodo che apre la finestra Home.
     * @param event Evento proveniente dalla GUI
     * @throws IOException Gestione delle eccezioni di I/O
     */
    @FXML public void switchToHomeScene(ActionEvent event)  throws IOException {
            root = FXMLLoader.load(getClass().getResource("../GUI/Home.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            //stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }

    /**
     * Metodo che apre una finestra elencando gli errori passati in input.
     * @param errors Stringhe contenenti gli errori riscontrati
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
