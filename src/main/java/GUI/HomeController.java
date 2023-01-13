package GUI;
import Model.Employee;
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
                        stage.setScene(scene);
                        EmployeeListController controller = new EmployeeListController();
                        controller.addEmployeeList(new Employee("12345678910121322","Manuel", "Mignogna","3465013137","Junior",1500));
                        stage.show();
                }
    }
