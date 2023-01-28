package GUI;
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
                @FXML
                public void switchToEmployeeListScene(ActionEvent event) throws IOException {
                        Parent root = FXMLLoader.load(getClass().getResource("../GUI/EmployeeList.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setMaximized(true);
                        stage.setScene(scene);
                        EmployeeListController controller = new EmployeeListController();
                        controller.addEmployeeList(new Employee("12345678910121322","Manuel", "Mignogna","3465013137","Junior",1500));
                        controller.addEmployeeList(new Employee("12345678910121321","Manuel", "Mignogna","3465013137","Executive",1500));
                        stage.show();
                }
                @FXML
                public void switchToLabListScene(ActionEvent event) throws IOException {
                        Parent root = FXMLLoader.load(getClass().getResource("../GUI/LaboratoryList.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setMaximized(true);
                        stage.setScene(scene);
                        LaboratoryListController controller = new LaboratoryListController();
                        controller.AddLaboratoryList(new Laboratory("Lab1","topic",
                              new Employee("12345678910121322","Manuel", "Mignogna","3465013137","Junior",1500),
                               new Project("123","project1",123, LocalDate.of(2024,10,10),
                                      new Employee("12345678910121322","Manuel", "Mignogna","3465013137","Junior",1500),
                                      new Employee("12345678910121322","Manuel", "Mignogna","3465013137","Junior",1500))));
                        stage.show();
                }
                @FXML
                public void switchToProjectsListScene(ActionEvent event) throws IOException {
                        Parent root = FXMLLoader.load(getClass().getResource("../GUI/ProjectList.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setMaximized(true);
                        stage.setScene(scene);
                        ProjectListController controller = new ProjectListController();
                        controller.AddProjectList(new Project("123","project1",123,
                                LocalDate.of(2024,12,10),
                                new Employee("12345678910121322","Manuel", "Mignogna","3465013137","Junior",1500),
                                new Employee("12345678910121322","Manuel", "Mignogna","3465013137","Junior",1500)));
                        stage.show();
                }
                @FXML
                public void switchToEquipmentListScene(ActionEvent event) throws IOException {
                        Parent root = FXMLLoader.load(getClass().getResource("../GUI/EquipmentList.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setMaximized(true);
                        stage.setScene(scene);
                        EquipmentListController controller= new EquipmentListController();
                        controller.addEquipment(new Equipment(12,"nomeEqui","descripion",123,
                                new Project("123","project1",123,
                                        LocalDate.of(2024,12,10),
                                        new Employee("12345678910121322","Manuel", "Mignogna","3465013137","Junior",1500),
                                        new Employee("12345678910121322","Manuel", "Mignogna","3465013137","Junior",1500)),
                                new Laboratory("Lab1","topic",
                                        new Employee("12345678910121322","Manuel", "Mignogna","3465013137","Junior",1500),
                                        new Project("123","project1",123, LocalDate.of(2024,10,10),
                                                new Employee("12345678910121322","Manuel", "Mignogna","3465013137","Junior",1500),
                                                new Employee("12345678910121322","Manuel", "Mignogna","3465013137","Junior",1500))),
                                "dealer"));

                        stage.show();
                }
    }
