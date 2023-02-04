package GUI;

import Controller.Controller;
import Model.TemporaryEmployee;
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

public class TemporaryEmployeeListController implements Initializable {
    @FXML private TableView<TemporaryEmployee> TemporaryEmployeesTable;
    @FXML private TableColumn<TemporaryEmployee, String> AddressTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> EmploymentDateTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> FirstNameTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> LaboratoryTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> projectTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> LastNameTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> SalaryTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> emailTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> phoneNumTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> ssnTemporaryEmployeeTable;
    @FXML private Button fireButton;
    @FXML private Button hireButton;
    @FXML private Button modifyButton;

    @FXML
    void hireTemporaryEmployee(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/addTemporaryEmployee.fxml"));
        scene = new Scene(root);
        stage= new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    Controller controller;
    static public ObservableList<TemporaryEmployee>list= FXCollections.observableArrayList();
    public void loadList(){
        list.addAll(controller.getTemporaryEmployeeController().getTemporaryEmployeeArrayList());
    }
    public void addList(TemporaryEmployee employee){
        list.add(employee);
    }
    public void initialize(URL url, ResourceBundle rb){
        FirstNameTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee,String>("firstName"));
        LastNameTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee, String>("lastName"));
        AddressTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee, String>("address"));
        EmploymentDateTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee, String>("employmentDate"));
        LaboratoryTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee, String>("labName"));
        SalaryTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee, String>("salary"));
        emailTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee, String>("email"));
        phoneNumTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee,String>("phoneNum"));
        ssnTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee,String>("SSN"));
        projectTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee,String>("projectCup"));
        TemporaryEmployeesTable.setItems(list);
    }
    @FXML public int getSelectedTemporaryEmployeeIndex(){
        return TemporaryEmployeesTable.getSelectionModel().getSelectedIndex();
    }
    @FXML public void fireTemporaryEmployee(){
        list.remove(getSelectedTemporaryEmployeeIndex());
    }
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML public void switchToHomeScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/Home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
       // stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    @FXML public void hireTemporaryEmployee() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/addTemporaryEmployee.fxml"));
        scene = new Scene(root);
        stage= new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
    @FXML void modifyTemporaryEmployee()throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/modifyTemporaryEmployee.fxml"));
        root=loader.load();
        stage= new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        modifyTemporaryEmployeeController controller= loader.getController();
        controller.setTemporaryEmployeeIndex(getSelectedTemporaryEmployeeIndex());
        stage.showAndWait();
        TemporaryEmployeesTable.refresh();
    }
}
