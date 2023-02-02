package GUI;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class EquipmentListController implements Initializable {
    @FXML private TableView<Equipment> EquipmentTable;
    @FXML private TableColumn<Equipment , String> equipmentDealerTable;
    @FXML private TableColumn<Equipment , String>  equipmentDescriptionTable;
    @FXML private TableColumn<Equipment , String>  equipmentLaboratoryTable;
    @FXML private TableColumn<Equipment , String>  equipmentNameTable;
    @FXML private TableColumn<Equipment , String>  equipmentPriceTable;
    @FXML private TableColumn<Equipment , String>  equipmentProjectTable;
    @FXML private TableColumn<Equipment , String>  equipmentPurchaseDateTable;
    @FXML private TableColumn<Equipment , String>  idEquipmentTable;
    @FXML private Button modifyEquipmentButton;
    @FXML void modifyEquipment() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/modifyEquipment.fxml"));
        root=loader.load();
        stage= new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        modifyEquipmentController controller= loader.getController();
        controller.setEquipmentIndex(getSelectedEquipmentIndex());
        stage.showAndWait();
        EquipmentTable.refresh();
    }
    @FXML private Button buyEquipmentButton;
    @FXML void buyEquipment()throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/addEquipment.fxml"));
        scene = new Scene(root);
        stage= new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
    @FXML private Button sellEquipmentButton;
    @FXML public void sellEquipment(){
        list.remove(getSelectedEquipmentIndex());
    }
    @FXML public int getSelectedEquipmentIndex(){
        return EquipmentTable.getSelectionModel().getSelectedIndex();
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
    public static ObservableList<Equipment> list = FXCollections.observableArrayList();
    public void addEquipment(Equipment equipment){
        list.add(equipment);
    }
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        idEquipmentTable.setCellValueFactory(new PropertyValueFactory<Equipment,String>("id"));
        equipmentDealerTable.setCellValueFactory(new PropertyValueFactory<Equipment,String>("dealer"));
        equipmentDescriptionTable.setCellValueFactory(new PropertyValueFactory<Equipment,String>("description"));
        equipmentLaboratoryTable.setCellValueFactory(new PropertyValueFactory<Equipment,String>("labName"));
        equipmentNameTable.setCellValueFactory(new PropertyValueFactory<Equipment,String>("name"));
        equipmentPriceTable.setCellValueFactory(new PropertyValueFactory<Equipment,String>("price"));
        equipmentPurchaseDateTable.setCellValueFactory(new PropertyValueFactory<Equipment,String>("purchaseDate"));
        equipmentProjectTable.setCellValueFactory(new PropertyValueFactory<Equipment,String>("projectCup"));
        EquipmentTable.setItems(getEquipmentList());
    }
    public ObservableList<Equipment> getEquipmentList(){
        return list;
    }
}

