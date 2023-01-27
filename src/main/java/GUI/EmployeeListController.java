package GUI;

import Model.Employee;
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
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeListController implements Initializable {

    @FXML
    private TableView<Employee> EmployeesTable;

    @FXML
    private TableColumn<Employee, String> AddressEmployeeTable;
    @FXML
    private TableColumn<Employee, String> EmploymentDateEmployeeTable;

    @FXML
    private TableColumn<Employee, String> FirstNameEmployeeTable;

    @FXML
    private TableColumn<Employee, String> LaboratoryEmployeeTable;

    @FXML
    private TableColumn<Employee, String> LastNameEmployeeTable;

    @FXML
    private TableColumn<Employee, String> RoleEmployeeTable;

    @FXML
    private TableColumn<Employee, String> SalaryEmployeeTable;

    @FXML
    private TableColumn<Employee, String> emailEmployeeTable;

    @FXML
    private TableColumn<Employee, String> phoneNumEmployeeTable;

    @FXML
    private TableColumn<Employee, String> ssnEmployeeTable;
    @FXML
    private Button fireButton;

    @FXML
    private Button hireButton;

    @FXML
    private Button modifyButton;

    //Employee employee=new Employee("435","name", "surname","32423","Junior",123);
    static ObservableList<Employee> list= FXCollections.observableArrayList();
    public void addEmployeeList(Employee employee){
        list.add(employee);
    }
    public ObservableList<Employee> getEmployeeList(){
        return list;
    }
    public void initialize(URL url, ResourceBundle rb){
        FirstNameEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee,String>("firstName"));
        LastNameEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        AddressEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("address"));
        EmploymentDateEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("employmentDate"));
        LaboratoryEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("labName"));
        RoleEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("role"));
        SalaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("salary"));
        emailEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        phoneNumEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee,String>("phoneNum"));
        ssnEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee,String>("ssn"));

        EmployeesTable.setItems(getEmployeeList());
    }
    @FXML
    public int getSelectedEmployeeIndex(){
        return EmployeesTable.getSelectionModel().getSelectedIndex();
    }
    @FXML
    void modifyEmployee() {

    }

    @FXML
    public void fireEmployee(){
        list.remove(getSelectedEmployeeIndex());
    }

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public void switchToHomeScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/Home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void hireEmployee (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/addEmployee.fxml"));
        scene = new Scene(root);
        Stage stage= new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
