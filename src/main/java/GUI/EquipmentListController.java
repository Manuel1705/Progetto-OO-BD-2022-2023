package GUI;
import Controller.Controller;
import Model.Equipment;
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
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
public class EquipmentListController implements Initializable {
    @FXML private TableView<Equipment> EquipmentTable;
    @FXML private TableColumn<Equipment , String> equipmentDealerTable;
    @FXML private TableColumn<Equipment , String>  equipmentDescriptionTable;
    @FXML private TableColumn<Equipment , String>  equipmentLaboratoryTable;
    @FXML private TableColumn<Equipment , String>  equipmentNameTable;
    @FXML private TableColumn<Equipment , Float>  equipmentPriceTable;
    @FXML private TableColumn<Equipment , String>  equipmentProjectTable;
    @FXML private TableColumn<Equipment , LocalDate>  equipmentPurchaseDateTable;
    @FXML private TableColumn<Equipment , Integer>  idEquipmentTable;
    @FXML private Button buyEquipmentButton;
    @FXML private Button modifyEquipmentButton;
    private ObservableList<Equipment> list = FXCollections.observableArrayList();
    private Controller controller;

    /**
     * Metodo che carica l'equipaggiamento salvato dal controller nell'Observable List
     */
    private void loadList(){
        list.clear();
        list.addAll(controller.getEquipmentController().getEquipmentArrayList());
    }

    /**
     * Metodo che apre la finestra che permette di modificare l'equipaggiamento selezionato dall'utente.
     * @throws IOException
     */
    @FXML void modifyEquipment() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/modifyEquipment.fxml"));
        root=loader.load();
        stage= new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        modifyEquipmentController controller= loader.getController();
        int i = getSelectedEquipmentIndex();
        if(idEquipmentTable.getCellObservableValue(i) != null) {
            controller.setDefaultFields(idEquipmentTable.getCellObservableValue(i).getValue(),
                    equipmentDescriptionTable.getCellObservableValue(i).getValue(),
                    equipmentDealerTable.getCellObservableValue(i).getValue(),
                    equipmentLaboratoryTable.getCellObservableValue(i).getValue(),
                    equipmentProjectTable.getCellObservableValue(i).getValue(),
                    equipmentPriceTable.getCellObservableValue(i).getValue(),
                    equipmentNameTable.getCellObservableValue(i).getValue());
            stage.getIcons().add(new Image("app-icon.png"));
            stage.showAndWait();
            loadList();
        }
    }

    /**
     * Metodo che apre la finestra che permette di acquistare nuovo equipaggiamento.
     * @throws IOException
     */
    @FXML void buyEquipment()throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/addEquipment.fxml"));
        scene = new Scene(root);
        stage= new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.getIcons().add(new Image("app-icon.png"));
        stage.showAndWait();
        loadList();
    }
    @FXML private Button sellEquipmentButton;

    /**
     * Metodo che permette di rimuovere l'equipaggiamento selezionato dall'utente.
     */
    @FXML public void sellEquipment(){
        if(idEquipmentTable.getCellObservableValue(getSelectedEquipmentIndex()) != null) {
            controller.getEquipmentController().deleteEquipment(idEquipmentTable.getCellObservableValue(getSelectedEquipmentIndex()).getValue());
            loadList();
        }
    }

    /**
     * Metodo che restituisce l'indice della riga selezionata dall'utente.
     * @return
     */
    @FXML public int getSelectedEquipmentIndex(){
        return EquipmentTable.getSelectionModel().getSelectedIndex();
    }
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Metodo che permette di ritornare alla schermata home.
     * @param event
     * @throws IOException
     */
    @FXML void switchToHomeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../GUI/Home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        //stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo che inizializza la finestra.
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        //Il metodo ottiene un'istanza del controller.
        controller=Controller.getInstance();
        //Vengono caricati i dati del controller.
        loadList();
        //Vengono inizializzate le celle della tabella.
        idEquipmentTable.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("id"));
        equipmentDealerTable.setCellValueFactory(new PropertyValueFactory<Equipment,String>("dealer"));
        equipmentDescriptionTable.setCellValueFactory(new PropertyValueFactory<Equipment,String>("description"));
        equipmentLaboratoryTable.setCellValueFactory(new PropertyValueFactory<Equipment,String>("Name"));
        equipmentNameTable.setCellValueFactory(new PropertyValueFactory<Equipment,String>("name"));
        equipmentPriceTable.setCellValueFactory(new PropertyValueFactory<Equipment, Float>("price"));
        equipmentPurchaseDateTable.setCellValueFactory(new PropertyValueFactory<Equipment, LocalDate>("purchaseDate"));
        equipmentProjectTable.setCellValueFactory(new PropertyValueFactory<Equipment,String>("projectCup"));
        EquipmentTable.setItems(getEquipmentList());
    }

    /**
     * Metodo che restituisce l'Observable List usata dalla tabella.
     * @return
     */
    private ObservableList<Equipment> getEquipmentList(){
        return list;
    }
}

