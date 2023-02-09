package GUI;

import Controller.Controller;
import Model.CareerDevelopment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CareerDevelopmentListController implements Initializable {

    @FXML
    private TableView<CareerDevelopment> CarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> lastNameCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> dateCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> firstNameCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> newRoleCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> newSalaryCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> oldRoleCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> oldSalaryCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> ssnCarrerDevelpmentTable;
    Controller controller;
    static public ObservableList<CareerDevelopment> list= FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle rb){
        controller= Controller.getInstance();
        firstNameCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment,String>("firstName"));
        lastNameCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, String>("lastName"));
        newRoleCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, String>("newRole"));
        oldRoleCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, String>("oldRole"));
        newSalaryCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, String>("newSalary"));
        oldSalaryCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, String>("oldSalary"));
        ssnCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, String>("ssn"));
        dateCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, String>("date"));
        CarrerDevelpmentTable.setItems(list);
    }
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML public void switchToHomeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../GUI/Home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        // stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
