package GUI;
import Controller.Controller;
import Model.CompanyEmployee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Controller controller;

    /**
     * Metodo che verifica la validita' dell'input e passa al controller i dati inseriti dall'utente.
     */
    @FXML public void addProject() throws IOException{
        ArrayList<String> errors = new ArrayList<String>();

        try{
            Float.parseFloat(budgetAddProject.getText());
        }
        catch (Exception ex){
            errors.add("Budget must be a valid number.");
            budgetAddProject.setText("0");
        }
        errors.addAll(controller.getProjectController().checkProjectInsert( cupAddProject.getText(),
                nameAddProject.getText(),
                Float.parseFloat(budgetAddProject.getText()),
                endDateAddProject.getValue(),
                SrespAddProject.getValue(),
                SrefAddProject.getValue()));
        if(errors.isEmpty()){
            controller.getProjectController().addProjectList(
                    cupAddProject.getText(),
                    nameAddProject.getText(),
                    Float.parseFloat(budgetAddProject.getText()),
                    endDateAddProject.getValue(),
                    SrespAddProject.getValue(),
                    SrefAddProject.getValue());

            Stage stage = (Stage) addProjectButton.getScene().getWindow();
            stage.close();
        }
        else{
            showErrorWindow(errors);
        }
    }

    /**
     * Metodo che inizializza la finestra.
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        controller=Controller.getInstance();
        ArrayList<CompanyEmployee> employeeArrayList=controller.getEmployeeController().getEmployeeArrayList();
        for (CompanyEmployee employee: employeeArrayList) {
            if(employee.getRole().equals("Executive")){
                SrespAddProject.getItems().add(employee.getSSN());
            }
            else if(employee.getRole().equals("Senior")) {
                SrefAddProject.getItems().add(employee.getSSN());
                }
        }

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
}

