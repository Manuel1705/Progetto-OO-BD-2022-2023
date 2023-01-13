package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Controller
        //implements Initializable
        {

        @FXML
        private Label EmpNumLabel;

        @FXML
        private Pane EmployeesButton;

        @FXML
        private Pane LabButton;

        @FXML
        private Label LabNumLabel;

        @FXML
        private Pane ProjectButton;

        @FXML
        private Label ProjectNumLabel;
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

        //Employee employee=new Employee("435","name", "surname","32423","Junior",123);
        static ObservableList<Employee> list=FXCollections.observableArrayList();
        public void addEmployeeList(Employee employee){
                list.add(employee);
                System.out.println(list.size());
        }
        public ObservableList<Employee> getEmployeeList(){
                return list;
        }

       /* public void initialize(URL url, ResourceBundle rb){
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
                System.out.println("prova");
                //System.out.println(getEmployeeList().size());
                EmployeesTable.setItems(getEmployeeList());
        }*/

    }
