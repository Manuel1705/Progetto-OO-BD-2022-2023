package GUI;

import Controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DBConnectController implements Initializable {

    private Controller controller;
    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField databaseTextField;

    @FXML
    private Button databaseConnectButton;

    @FXML
    private ChoiceBox<String> DBMSDropdown;

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Metodo che viene chiamato quando l'utente conferma la connessione al database.
     */
    @FXML
    private void connectDB() throws IOException{
        if (!usernameTextField.getText().isBlank() && !passwordTextField.getText().isBlank()
                && !databaseTextField.getText().isBlank() && !DBMSDropdown.getValue().isBlank()) {
            ArrayList<String> errors = controller.setDB(usernameTextField.getText(), passwordTextField.getText(),
                    databaseTextField.getText(), DBMSDropdown.getValue());
            if (errors.isEmpty()) {
                Stage stage = (Stage) databaseConnectButton.getScene().getWindow();
                stage.close();
            } else showErrorWindow(errors);

        }
    }

    /**
     * Metodo che inizializza la finstra.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] supportedDBMS = {"PostgreSQL"};
        DBMSDropdown.getItems().addAll(supportedDBMS);
        controller = Controller.getInstance();

    }

    /**
     * Metodo che apre una finestra elencando gli errori passati in input.
     *
     * @param errors
     * @throws IOException
     */
    private void showErrorWindow(ArrayList<String> errors) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/ErrorWindow.fxml"));
        root = loader.load();
        stage = new Stage();
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
