package GUI;
import Controller.Controller;
import Model.Employee;
import Model.Equipment;
import Model.Laboratory;
import Model.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class HomeController
        {
                @FXML
                private Label EmpNumLabel;

                @FXML
                private Pane EmployeesButton;

                @FXML
                private Pane EquipmentButton;

                @FXML
                private Label EquipmentNumLabel;

                @FXML
                private Pane LabButton;

                @FXML
                private Label LabNumLabel;

                @FXML
                private Pane ProjectButton;

                @FXML
                private Label ProjectNumLabel;
                private Stage stage;
                private Scene scene;
                private Parent root;
                Controller controller;
                @FXML
                public void switchToEmployeeListScene(ActionEvent event) throws IOException {
                        Parent root = FXMLLoader.load(getClass().getResource("../GUI/EmployeeList.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setMaximized(true);
                        stage.setScene(scene);
                        stage.show();
                }
                @FXML
                public void switchToLabListScene(ActionEvent event) throws IOException {
                        Parent root = FXMLLoader.load(getClass().getResource("../GUI/LaboratoryList.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setMaximized(true);
                        stage.setScene(scene);
                        stage.show();
                }
                @FXML
                public void switchToProjectsListScene(ActionEvent event) throws IOException {
                        Parent root = FXMLLoader.load(getClass().getResource("../GUI/ProjectList.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setMaximized(true);
                        stage.setScene(scene);
                        stage.show();
                }
                @FXML
                public void switchToEquipmentListScene(ActionEvent event) throws IOException {
                        Parent root = FXMLLoader.load(getClass().getResource("../GUI/EquipmentList.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setMaximized(true);
                        stage.setScene(scene);
                        stage.show();
                }
    }
