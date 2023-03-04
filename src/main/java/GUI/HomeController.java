package GUI;
import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

        @FXML
        private Pane EmployeesButton;
        @FXML
        private Pane EquipmentButton;
        @FXML
        private Pane LabButton;
        @FXML
        private Label databaseConnectionStateLabel;
        @FXML
        private Pane ProjectButton;
        @FXML
        private Pane TemporaryEmployeesButton;

        private Stage stage;
        private Scene scene;
        private Parent root;
        private Controller controller;

        /**
         * Metodo che apre la finestra dell'elenco degli impiegati e aggiorna i dati.
         * @param event È stato premuto l'apposito bottone
         * @throws IOException Gestione delle eccezioni di I/O
         */
        @FXML
        public void switchToEmployeeListScene(ActionEvent event) throws IOException {
                controller.updateData();
                root = FXMLLoader.load(getClass().getResource("../GUI/EmployeeList.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                //stage.setMaximized(true);
                stage.setScene(scene);
                stage.show();
        }

        /**
         * Metodo che apre la finestra dell'elenco dei laboratori e aggiorna i dati.
         * @param event È stato premuto l'apposito bottone
         * @throws IOException Gestione delle eccezioni di I/O
         */
        @FXML
        public void switchToLabListScene(ActionEvent event) throws IOException {
                controller.updateData();
                root = FXMLLoader.load(getClass().getResource("../GUI/LaboratoryList.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                //stage.setMaximized(true);
                stage.setScene(scene);
                stage.show();
        }

        /**
         * Metodo che apre la finestra dell'elenco dei progetti e aggiorna i dati.
         * @param event È stato premuto l'apposito bottone
         * @throws IOException Gestione delle eccezioni di I/O
         */
        @FXML
        public void switchToProjectsListScene(ActionEvent event) throws IOException {
                controller.updateData();
                root = FXMLLoader.load(getClass().getResource("../GUI/ProjectList.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                //stage.setMaximized(true);
                stage.setScene(scene);
                stage.show();
        }

        /**
         * Metodo che apre la finestra dell'elenco dell'equipaggiamento e aggiorna i dati.
         * @param event È stato premuto l'apposito bottone
         * @throws IOException Gestione delle eccezioni di I/O
         */
        @FXML
        public void switchToEquipmentListScene(ActionEvent event) throws IOException {
                controller.updateData();
                root = FXMLLoader.load(getClass().getResource("../GUI/EquipmentList.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                //stage.setMaximized(true);
                stage.setScene(scene);
                stage.show();
        }

        /**
         * Metodo che apre la finestra dell'elenco degli impiegati temporanei e aggiorna i dati.
         * @param event È stato premuto l'apposito bottone
         * @throws IOException Gestione delle eccezioni di I/O
         */
        @FXML
        public void switchToTemporaryEmployeeListScene(ActionEvent event) throws IOException {
                controller.updateData();
                root = FXMLLoader.load(getClass().getResource("../GUI/TemporaryEmployeeList.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                //stage.setMaximized(true);
                stage.setScene(scene);
                stage.show();
        }

        /**
         * Metodo che apre la finestra che permette di collegare il database.
         * @param event È stato premuto l'apposito bottone
         * @throws IOException Gestione delle eccezioni di I/O
         */
        @FXML
        public void switchToDBConnectScene(ActionEvent event) throws IOException {
                Parent root = FXMLLoader.load(getClass().getResource("../GUI/DBConnect.fxml"));
                scene = new Scene(root);
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.getIcons().add(new Image("app-icon.png"));
                stage.showAndWait();
                if(controller.isDBConnected()) {
                        databaseConnectionStateLabel.setText("Database is connected");
                        databaseConnectionStateLabel.setStyle("-fx-text-fill: #029e02;");
                }
                else {
                        databaseConnectionStateLabel.setText("Database is not connected");
                        databaseConnectionStateLabel.setStyle("-fx-text-fill: red;");
                }

        }

        /**
         * Metodo che inizializza la finestra.
         * @param url
         * @param resourceBundle
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                controller = Controller.getInstance();
                if(controller.isDBConnected()) {
                        databaseConnectionStateLabel.setText("Database is connected");
                        databaseConnectionStateLabel.setStyle("-fx-text-fill: #029e02;");
                }
                else {
                        databaseConnectionStateLabel.setText("Database is not connected");
                        databaseConnectionStateLabel.setStyle("-fx-text-fill: red;");

                }

        }
}
