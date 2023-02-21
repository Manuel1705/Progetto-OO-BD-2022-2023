package GUI;
import Controller.Controller;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class EmployeeListController implements Initializable {

    @FXML public TableView<Employee> EmployeesTable;
    @FXML private TableColumn<Employee, String> AddressEmployeeTable;
    @FXML private TableColumn<Employee, LocalDate> EmploymentDateEmployeeTable;
    @FXML private TableColumn<Employee, String> FirstNameEmployeeTable;
    @FXML private TableColumn<Employee, String> LaboratoryEmployeeTable;
    @FXML private TableColumn<Employee, String> LastNameEmployeeTable;
    @FXML private TableColumn<Employee, String> RoleEmployeeTable;
    @FXML private TableColumn<Employee, Float> SalaryEmployeeTable;
    @FXML private TableColumn<Employee, String> emailEmployeeTable;
    @FXML private TableColumn<Employee, String> phoneNumEmployeeTable;
    @FXML private TableColumn<Employee, String> ssnEmployeeTable;
    @FXML private Button fireButton;
    @FXML private Button hireButton;
    @FXML private Button modifyButton;
    @FXML private Button CareerChanges;
    Controller controller;
    public ObservableList<Employee> list= FXCollections.observableArrayList();

    /**
     * Metodo che carica gli impiegati salvati dal controller nell'Observable List
     */
    public void loadList(){
        list.clear();
        list.addAll(controller.getEmployeeController().getEmployeeArrayList());
    }

    /**
     * Metodo che inizializza la finestra.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb){
        //Il metodo trova il controller
        controller = Controller.getInstance();

        //Quando viene inizializzata la finestra i dati vengono caricati dal controller.
        loadList();


        //Inserisce gli elementi della lista in tabella

        FirstNameEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee,String>("firstName"));
        LastNameEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        AddressEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("address"));
        EmploymentDateEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, LocalDate>("employmentDate"));
        LaboratoryEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("labName"));
        RoleEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("role"));
        SalaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, Float>("salary"));
        emailEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        phoneNumEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee,String>("phoneNum"));
        ssnEmployeeTable.setCellValueFactory(new PropertyValueFactory<Employee,String>("SSN"));
        EmployeesTable.setItems(list);
    }

    /**
     * Metodo che restituisce l'indice della riga selezionata dall'utente nella tabella.
     * @return
     */
    @FXML public int getSelectedEmployeeIndex(){
        return EmployeesTable.getSelectionModel().getSelectedIndex();
    }

    /**
     * Metodo che elimina l'impiegato selezionato dall'utente usando il controller.
     */
    @FXML public void fireEmployee() throws IOException{
        String selectedSsn = ssnEmployeeTable.getCellObservableValue(getSelectedEmployeeIndex()).getValue();
        ArrayList<String> errors = controller.getEmployeeController().checkEmployeeDelete(selectedSsn);
        if(errors.isEmpty())
            controller.getEmployeeController().fireEmployee(selectedSsn);
        else{
            showErrorWindow(errors);
        }
        loadList();
    }

    /**
     * Metodo che apre una finestra elencando gli errori passati in input.
     * @param errors
     * @throws IOException
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
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Metodo che ritorna alla finestra Home.
     * @param event
     * @throws IOException
     */
    @FXML public void switchToHomeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../GUI/Home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
       // stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo che apre la finestra degli scatti di carriera.
     * @param event
     * @throws IOException
     */
    @FXML public void careerChanges(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../GUI/CareerDevelopment.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        // stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo che apre la finestra per assumere un nuovo impiegato.
     * @throws IOException
     */
    @FXML public void hireEmployee() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/addEmployee.fxml"));
        scene = new Scene(root);
        stage= new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.getIcons().add(new Image("app-icon.png"));
        stage.showAndWait();
        loadList();
    }

    /**
     * Metodo che apre la finestra per modificare l'impiegato selezionato dall'utente.
     * @throws IOException
     */
    @FXML void modifyEmployee()throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/modifyEmployee.fxml"));
        root=loader.load();
        stage= new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);

        modifyEmployeeController modifyController = loader.getController();
        int i = getSelectedEmployeeIndex();
        modifyController.setDefaultFields(ssnEmployeeTable.getCellObservableValue(i).getValue(),
                FirstNameEmployeeTable.getCellObservableValue(i).getValue(),
                LastNameEmployeeTable.getCellObservableValue(i).getValue(),
                phoneNumEmployeeTable.getCellObservableValue(i).getValue(),
                AddressEmployeeTable.getCellObservableValue(i).getValue(),
                RoleEmployeeTable.getCellObservableValue(i).getValue(),
                LaboratoryEmployeeTable.getCellObservableValue(i).getValue(),
                SalaryEmployeeTable.getCellObservableValue(i).getValue().toString() ,
                emailEmployeeTable.getCellObservableValue(i).getValue(),
                EmploymentDateEmployeeTable.getCellObservableValue(i).getValue().toString());
        stage.getIcons().add(new Image("app-icon.png"));
        stage.showAndWait();
        loadList();
    }
}
