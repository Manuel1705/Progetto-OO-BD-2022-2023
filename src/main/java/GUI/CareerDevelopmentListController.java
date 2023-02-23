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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class  CareerDevelopmentListController implements Initializable {

    @FXML
    private TableView<CareerDevelopment> CarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> lastNameCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, LocalDate> dateCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> firstNameCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> newRoleCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, Float> newSalaryCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> oldRoleCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> oldSalaryCarrerDevelpmentTable;

    @FXML
    private TableColumn<CareerDevelopment, String> ssnCarrerDevelpmentTable;
    private Controller controller;
    public ObservableList<CareerDevelopment> list= FXCollections.observableArrayList();

    /**
     * Metodo che carica i dati dal controller.
     */
    public void loadList(){
        list.clear();
        list.addAll(controller.getCareerDevelopmentController().getCareerDevelopmentArrayList());
    }

    /**
     * Metodo che inizializza la finestra.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb){
        //Ottiene l'istanza del controller
        controller = Controller.getInstance();
        //Carica i dati dal controller
        loadList();

        //Inizializza la tabella
        firstNameCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment,String>("FirstName"));
        lastNameCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, String>("LastName"));
        newRoleCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, String>("NewRole"));
        oldRoleCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, String>("OldRole"));
        newSalaryCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, Float>("newSalary"));
        oldSalaryCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, String>("oldSalary"));
        ssnCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, String>("SSN"));
        dateCarrerDevelpmentTable.setCellValueFactory(new PropertyValueFactory<CareerDevelopment, LocalDate>("date"));
        CarrerDevelpmentTable.setItems(list);
    }
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Metodo che viene chiamato quando l'utente chiude la finestra.
     * @param event
     * @throws IOException
     */
    @FXML public void switchToEmployeeList(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../GUI/EmployeeList.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        // stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
