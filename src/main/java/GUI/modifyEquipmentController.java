package GUI;
import Controller.Controller;
import Model.Equipment;
import Model.Laboratory;
import Model.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.util.ArrayList;
public class modifyEquipmentController {
    @FXML private TextField dealerModifyEquipment;
    @FXML private TextArea descriptionModifyEquipment;
    @FXML private TextField idEquipmentModifyEquipment;
    @FXML private ChoiceBox<String> labModifyEquipment;
    @FXML private Button modifyEquipmentButton;
    @FXML private TextField nameModifyEquipment;
    @FXML private TextField priceModifyEquipment;
    @FXML private ChoiceBox<String> projectModifyEquipment;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Controller controller;

    /**
     * Metodo che inizializza i campi della finestra.
     * @param id            Codice identificativo dell'equipaggiamento
     * @param description   Descrizione dell'equipaggiamento
     * @param dealer        Colui che ha venduto l'equipaggiamento
     * @param labName       Nome del laboratorio che ha fatto richiesta dell'equipaggiamento
     * @param projectCup    Cup del progetto per cui è stato richiesto l'equipaggiamento
     * @param price         Prezzo dell'equipaggiamento
     * @param name          Nome dell'equipaggiamento
     */
    public void setDefaultFields(int id, String description, String dealer, String labName, String projectCup, float price,
                                  String name){
        controller=Controller.getInstance();
        ArrayList<Laboratory> labs = controller.getLaboratoryController().getLaboratoryArrayList();
        labModifyEquipment.getItems().add("");
        for (Laboratory lab: labs) {
            labModifyEquipment.getItems().add(lab.getName());
        }
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        for (Project project:projectArrayList) {
            projectModifyEquipment.getItems().add(project.getCup());
        }

        idEquipmentModifyEquipment.setText(Integer.toString(id));
        nameModifyEquipment.setText(name);
        dealerModifyEquipment.setText(dealer);
        descriptionModifyEquipment.setText(description);
        priceModifyEquipment.setText(Float.toString(price));
        labModifyEquipment.setValue(labName);
        projectModifyEquipment.setValue(projectCup);
    }

    /**
     * Metodo che viene chiamato quando l'utente conferma la modifica.
     * @throws IOException Gestione delle eccezioni di I/O
     */
    @FXML void modifyEquipment() throws IOException{
        ArrayList<String> errors = controller.getEquipmentController().checkEquipmentModify(
                nameModifyEquipment.getText(),
                descriptionModifyEquipment.getText());


        if (errors.isEmpty()) {
            controller.getEquipmentController().modifyEquipment(Integer.parseInt(idEquipmentModifyEquipment.getText()),
                    nameModifyEquipment.getText(),
                    descriptionModifyEquipment.getText(),
                    labModifyEquipment.getValue());
            Stage stage = (Stage) modifyEquipmentButton.getScene().getWindow();
            stage.close();
        } else showErrorWindow(errors);
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
