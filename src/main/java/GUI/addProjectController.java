package GUI;
import Controller.Controller;
import Model.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class addProjectController implements Initializable {
    @FXML private Button addProjectButton;
    @FXML private TextField budgetAddProject;
    @FXML private TextField cupAddProject;
    @FXML private DatePicker endDateAddProject;
    @FXML private TextField nameAddProject;
    @FXML private ChoiceBox<String> SrefAddProject;
    @FXML private ChoiceBox<String> SrespAddProject;
    private Controller controller;

    /**
     * Metodo che verifica la validita' dell'input e passa al controller i dati inseriti dall'utente.
     */
    @FXML void addProject() {
        if(!cupAddProject.getText().isBlank() &&
            !nameAddProject.getText().isBlank() &&
            !budgetAddProject.getText().isBlank() &&
            !SrefAddProject.getValue().isBlank() &&
            !SrespAddProject.getValue().isBlank() &&
            endDateAddProject.getValue().compareTo(LocalDate.now())>0) {
            boolean check = true;
            //Controllo sulla lunghezza del CUP
            if (cupAddProject.getText().length() != 15) {
                check = false;
            }
            //Controllo sulla lunghezza del nome
            if(nameAddProject.getText().length()>30){
                check=false;
            }
            if (check) {
                controller.getProjectController().addProjectList(
                        cupAddProject.getText(),
                        nameAddProject.getText(),
                        budgetAddProject.getText(),
                        endDateAddProject.getValue(),
                        SrespAddProject.getValue(),
                        SrefAddProject.getValue());

                Stage stage = (Stage) addProjectButton.getScene().getWindow();
                stage.close();
            }
        }
    }

    /**
     * Metodo che inizializza la finestra.
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        controller=Controller.getInstance();
        ArrayList<Employee> employeeArrayList=controller.getEmployeeController().getEmployeeArrayList();
        //SrefAddProject.getItems().add("Empty Position");
        //SrespAddProject.getItems().add("Empty Position");
        for (Employee employee: employeeArrayList) {
            if(employee.getRole().equals("Executive")){
                SrespAddProject.getItems().add(employee.getSSN());
            }
            else if(employee.getRole().equals("Senior")) {
                SrefAddProject.getItems().add(employee.getSSN());
                }
        }

    }
}

