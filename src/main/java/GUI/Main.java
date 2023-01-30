package GUI;

import Model.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent homeRoot = FXMLLoader.load(getClass().getResource("../GUI/Home.fxml"));
        Scene home = new Scene(homeRoot);
        stage.setTitle("Project2022-2023");
        stage.setMaximized(true);
        stage.setScene(home);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }

}