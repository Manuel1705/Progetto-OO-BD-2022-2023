package GUI;
import Controller.Controller;
import Model.Employee;
import Model.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class modifyProjectController {

    @FXML
    private ChoiceBox<String> SrefModifyProject;

    @FXML
    private ChoiceBox<String> SrespModifyProject;

    @FXML
    private TextField budgetModifyProject;

    @FXML
    private TextField cupModifyProject;

    @FXML
    private DatePicker endDateModifyProject;

    @FXML
    private Button modifyProjectButton;

    @FXML
    private TextField nameModifyProject;
    private Controller controller;

    private String cup;

    /**
     * Metodo che inizializza i campi della finestra.
     * @param cup
     * @param name
     * @param budget
     * @param sResp
     * @param sRef
     * @param endDate
     */
    public void setDefaultFields(String cup, String name, String budget, String sResp, String sRef,
                                 String endDate)
    {
        controller=Controller.getInstance();
        this.cup = cup;
        ArrayList<Employee> employeeArrayList=controller.getEmployeeController().getEmployeeArrayList();
        for (Employee employee: employeeArrayList) {
            if(employee.getRole().equals("Executive")){
                SrespModifyProject.getItems().add(employee.getSSN());
            }
            else if(employee.getRole().equals("Senior")) {
                SrefModifyProject.getItems().add(employee.getSSN());
            }
        }
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        cupModifyProject.setText(cup);
        nameModifyProject.setText(name);
        budgetModifyProject.setText(budget);
        endDateModifyProject.setValue(LocalDate.parse(endDate));
        SrefModifyProject.setValue(sRef);
        SrespModifyProject.setValue(sResp);
    }

    /**
     * Metodo che viene chiamato quando l'utente conferma la modifica del progetto.
     * Verifica la validita' dell'input e passa i dati al controller.
     */
    @FXML
    void modifyProject() {
        boolean check = true;
        if (cupModifyProject.getText().length() != 15) {
            check = false;
        }
        if(nameModifyProject.getText().length()>30){
            check=false;
        }
        if (check) {
            controller.getProjectController().modifyProjectList(cup,
                    nameModifyProject.getText(),
                    budgetModifyProject.getText(),
                    endDateModifyProject.getValue(),
                    SrefModifyProject.getValue(),
                    SrespModifyProject.getValue());
            Stage stage = (Stage) modifyProjectButton.getScene().getWindow();
            stage.close();
        }
    }
}
