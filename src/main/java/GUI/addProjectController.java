package GUI;

import Model.Employee;
import Model.Laboratory;
import Model.Project;
import javafx.event.ActionEvent;
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

    @FXML
    private Button addProjectButton;

    @FXML
    private TextField budgetAddProject;

    @FXML
    private TextField cupAddProject;

    @FXML
    private DatePicker endDateAddProject;

    @FXML
    private TextField nameAddProject;

    @FXML
    private ChoiceBox<String> scientificReferentAddProject;

    @FXML
    private ChoiceBox<String> scientificResponsibleAddProject;

    @FXML
    void addProject(ActionEvent event) {
        ProjectListController projectListController= new ProjectListController();
        Project project = new Project(cupAddProject.getText(),
                nameAddProject.getText(),
                Float.parseFloat(budgetAddProject.getText()),
                endDateAddProject.getValue());


        project.setRefSSN(scientificReferentAddProject.getValue());
        if(!scientificReferentAddProject.getValue().equals("Null")) {
            int i = 0;
            while (!EmployeeListController.list.get(i).getSsn().equals(scientificReferentAddProject.getValue())) {
                i++;
            }
            project.setRef(EmployeeListController.list.get(i));
        }
        else { project.setRef(null); }

        project.setSrespSSN(scientificResponsibleAddProject.getValue());
        if(!scientificResponsibleAddProject.getValue().equals("Null")) {
                int i = 0;
                while (!EmployeeListController.list.get(i).getSsn().equals(scientificResponsibleAddProject.getValue())) {
                    i++;
                }
                project.setResp(EmployeeListController.list.get(i));
        }
        else { project.setResp(null); }
        System.out.println(endDateAddProject.converterProperty().toString());
        if(!cupAddProject.getText().isBlank() &&
            !nameAddProject.getText().isBlank()&&
        endDateAddProject.getValue().compareTo(LocalDate.now())>0) {
            projectListController.AddProjectList(project);
            Stage stage = (Stage) addProjectButton.getScene().getWindow();
            stage.close();
        }
    }
    ArrayList<Employee> scientificResponsibles= new ArrayList<Employee>();
    ArrayList<String> scientificResponsiblesSSN= new ArrayList<String>();
    ArrayList<Employee> scietificReferents= new ArrayList<Employee>();
    ArrayList<String> scietificReferentsSSN= new ArrayList<String>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            scientificResponsiblesSSN.add("Null");
            scietificReferentsSSN.add("Null");
        for (Employee employye: EmployeeListController.list) {
            if(employye.getRole().equals("Executive")){
                scientificResponsibles.add(employye);
                scientificResponsiblesSSN.add(employye.getSsn());
            }
            else if(employye.getRole().equals("Senior")) {
                scietificReferents.add(employye);
                scietificReferentsSSN.add(employye.getSsn());
                }
        }
            scientificResponsibleAddProject.getItems().addAll(scientificResponsiblesSSN);
            scientificReferentAddProject.getItems().addAll(scietificReferentsSSN);

    }
    }

