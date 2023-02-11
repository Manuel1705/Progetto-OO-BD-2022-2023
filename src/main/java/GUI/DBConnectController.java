package GUI;

import Controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DBConnectController implements Initializable {

    private Controller controller;
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField databaseTextField;

    @FXML
    private Button databaseConnectButton;

    @FXML
    private ChoiceBox<String> DBMSDropdown;

    @FXML
    private void connectDB(){
        if(!usernameTextField.getText().isBlank() && !passwordTextField.getText().isBlank()
          && !databaseTextField.getText().isBlank())
        {
            controller.setDB(usernameTextField.getText(), passwordTextField.getText(),
                    databaseTextField.getText(), DBMSDropdown.getValue());
            Stage stage = (Stage) databaseConnectButton.getScene().getWindow();
            stage.close();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] supportedDBMS = {"PostgreSQL"};
        DBMSDropdown.getItems().addAll(supportedDBMS);
        controller = Controller.getInstance();

    }
}
