package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{

    /**
     * Metodo che carica la schermata Home.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent homeRoot = FXMLLoader.load(getClass().getResource("../GUI/Home.fxml"));
        Scene home = new Scene(homeRoot);
        stage.setTitle("Project2022-2023");
        //stage.setMaximized(true);
        stage.setScene(home);
        stage.getIcons().add(new Image("app-icon.png"));
        stage.show();
    }

    /**
     * Funzione main del programma.
     * @param args
     */
    public static void main(String[] args){

        launch(args);
    }

}