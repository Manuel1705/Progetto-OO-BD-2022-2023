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
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TemporaryEmployeeListController implements Initializable {
    @FXML private TableView<TemporaryEmployee> TemporaryEmployeesTable;
    @FXML private TableColumn<TemporaryEmployee, String> AddressTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee, LocalDate> EmploymentDateTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> FirstNameTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> LaboratoryTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> projectTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> LastNameTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee, Float> SalaryTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> emailTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> phoneNumTemporaryEmployeeTable;
    @FXML private TableColumn<TemporaryEmployee,String> ssnTemporaryEmployeeTable;
    @FXML private Button fireButton;
    @FXML private Button hireButton;
    @FXML private Button modifyButton;
    private Controller controller;

    private ObservableList<TemporaryEmployee> list= FXCollections.observableArrayList();


    /**
     * Metodo che carica in memoria i dati del controller.
     */
    public void loadList(){
        list.clear();
        list.addAll(controller.getTemporaryEmployeeController().getTemporaryEmployeeArrayList());
    }

    /**
     * Metodo che inizializza la finestra
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb){
        //Viene istanziato il controller
        controller = Controller.getInstance();
        //Vengono caricati i dati del controller
        loadList();
        //Venogno inizializzate le celle della tabella.
        FirstNameTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee,String>("firstName"));
        LastNameTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee, String>("lastName"));
        AddressTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee, String>("address"));
        EmploymentDateTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee, LocalDate>("employmentDate"));
        LaboratoryTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee, String>("labName"));
        SalaryTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee, Float>("salary"));
        emailTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee, String>("email"));
        phoneNumTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee,String>("phoneNum"));
        ssnTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee,String>("SSN"));
        projectTemporaryEmployeeTable.setCellValueFactory(new PropertyValueFactory<TemporaryEmployee,String>("projectCup"));
        TemporaryEmployeesTable.setItems(list);
    }

    /**
     * Metodo che restiusice l'indice della riga selezionata dall'utente.
     * @return L'indice di riga
     */
    @FXML public int getSelectedTemporaryEmployeeIndex(){
        return TemporaryEmployeesTable.getSelectionModel().getSelectedIndex();
    }

    /**
     * Metodo che rimuove l'impiegato selezionato dall'utente.
     */
    @FXML public void fireTemporaryEmployee() throws IOException{
        if(ssnTemporaryEmployeeTable.getCellObservableValue(getSelectedTemporaryEmployeeIndex()) != null) {
            String selectedSsn = ssnTemporaryEmployeeTable.getCellObservableValue(getSelectedTemporaryEmployeeIndex()).getValue();
            String selectedProject = projectTemporaryEmployeeTable.getCellObservableValue(getSelectedTemporaryEmployeeIndex()).getValue();
            ArrayList<String> errors = controller.getTemporaryEmployeeController().checkTemporaryEmployeeDelete(selectedSsn);
            if (errors.isEmpty())
                controller.getTemporaryEmployeeController().fireTemporaryEmployee(selectedSsn, selectedProject);
            else {
                showErrorWindow(errors);
            }
            loadList();
        }
    }
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Metodo che permette di ritornare alla schermata Home.
     * @param event Viene premuto l'apposito bottone
     * @throws IOException Gestione delle eccezioni di I/O
     */
    @FXML public void switchToHomeScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/Home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
       // stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo che apre la finestra che permette di inserire un nuovo impiegato temporaneo.
     * @throws IOException Gestione delle eccezioni di I/O
     */
    @FXML public void hireTemporaryEmployee() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/addTemporaryEmployee.fxml"));
        scene = new Scene(root);
        stage= new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.getIcons().add(new Image("app-icon.png"));
        stage.showAndWait();
        loadList();
    }

    /**
     * Metodo che apre la finestra che permette di modificare l'impiegato temporaneo selezionato dall'utente.
     * @throws IOException Gestione delle eccezioni di I/O
     */
    @FXML public void modifyTemporaryEmployee()throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/modifyTemporaryEmployee.fxml"));
        root=loader.load();
        stage= new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        modifyTemporaryEmployeeController controller= loader.getController();
        int i = getSelectedTemporaryEmployeeIndex();
        if(ssnTemporaryEmployeeTable.getCellObservableValue(i) != null) {
            controller.setDefaultFields(ssnTemporaryEmployeeTable.getCellObservableValue(i).getValue(),
                    FirstNameTemporaryEmployeeTable.getCellObservableValue(i).getValue(),
                    LastNameTemporaryEmployeeTable.getCellObservableValue(i).getValue(),
                    phoneNumTemporaryEmployeeTable.getCellObservableValue(i).getValue(),
                    AddressTemporaryEmployeeTable.getCellObservableValue(i).getValue(),
                    projectTemporaryEmployeeTable.getCellObservableValue(i).getValue(),
                    LaboratoryTemporaryEmployeeTable.getCellObservableValue(i).getValue(),
                    SalaryTemporaryEmployeeTable.getCellObservableValue(i).getValue().toString(),
                    emailTemporaryEmployeeTable.getCellObservableValue(i).getValue());
            stage.getIcons().add(new Image("app-icon.png"));
            stage.showAndWait();
            loadList();
        }
    }

    /**
     * Metodo che apre una finestra elencando gli errori passati in input.
     * @param errors Stringhe di errori rilevati
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
