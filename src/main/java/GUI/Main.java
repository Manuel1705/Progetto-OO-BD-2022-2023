package GUI;

import Controller.Controller;
import Model.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent homeRoot = FXMLLoader.load(getClass().getResource("../Controller/EmployeeList.fxml"));
        Scene home = new Scene(homeRoot);
        stage.setTitle("Project2022-2023");
        stage.setWidth(1920);
        stage.setHeight(1080);

        stage.setScene(home);
        stage.show();
    }
    public static void main(String[] args){
        Controller controller = new Controller();
        controller.addEmployeeList(new Employee("12345678910121322","Manuel", "Mignogna","3465013137","Junior",1500));
        controller.addEmployeeList(new Employee("432","main", "surname","32423","Junior",123));
        controller.addEmployeeList(new Employee("433","main", "surname","32423","Junior",123));
        launch(args);
    }

}