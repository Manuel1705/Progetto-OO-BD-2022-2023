package GUI;
import Controller.Controller;
import Model.Laboratory;
import Model.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class addEquipmentController implements Initializable {
    @FXML private Button buyEquipmentButton;
    @FXML private TextField dealerAddEquipment;
    @FXML private TextArea descriptionAddEquipment;
    @FXML private TextField idEquipmentAddEquipment;
    @FXML private ChoiceBox<String> labAddEquipment;
    @FXML private TextField nameAddEquipment;
    @FXML private TextField priceAddEquipment;
    @FXML private ChoiceBox<String> projectAddEquipment;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Controller controller;

    /**
     * Metodo che viene chiamato quando l'utente conferma l'inserimento dei dati. Effettua i controlli sulla validita' dell'input.
     */
    @FXML public void buyEquipment() throws IOException{
        ArrayList<String> errors = new ArrayList<String>();

        try{
            Float.parseFloat(priceAddEquipment.getText());
        }
        catch (Exception ex){
            errors.add("Price must be a valid number.");
            priceAddEquipment.setText("0");
        }

        try{
            errors.addAll(controller.getEquipmentController().checkEquipmentInsert(Integer.parseInt(idEquipmentAddEquipment.getText()),
                    nameAddEquipment.getText(),
                    descriptionAddEquipment.getText(),
                    Float.parseFloat(priceAddEquipment.getText()),
                    dealerAddEquipment.getText(),
                    labAddEquipment.getValue(),
                    projectAddEquipment.getValue()));
        }
        catch (NumberFormatException ex){
            errors.add("ID must be a valid number.");
        }
        if (errors.isEmpty()) {
            controller.getEquipmentController().addEquipmentList(Integer.parseInt(idEquipmentAddEquipment.getText()),
                    nameAddEquipment.getText(),
                    descriptionAddEquipment.getText(),
                    Float.parseFloat(priceAddEquipment.getText()),
                    dealerAddEquipment.getText(),
                    labAddEquipment.getValue(),
                    projectAddEquipment.getValue());
            Stage stage = (Stage) buyEquipmentButton.getScene().getWindow();
            stage.close();
        }
        else showErrorWindow(errors);
    }


    /**
     * Metodo che inizializza la finestra.
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        controller=Controller.getInstance();
        //Il metodo inizializza il menu' a tendina dei laboratori.
        ArrayList<Laboratory> labs = controller.getLaboratoryController().getLaboratoryArrayList();
        labAddEquipment.getItems().add("");
        for (Laboratory lab: labs) {
            labAddEquipment.getItems().add(lab.getName());
        }
        //Il metodo inizializza il menu' a tendina dei progetti.
        ArrayList<Project>projectArrayList = controller.getProjectController().getProjectArrayList();
        for (Project project:projectArrayList) {
            projectAddEquipment.getItems().add(project.getCup());
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
