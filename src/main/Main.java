package main;

import controller.AnimationController;
import java.io.IOException;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    // when click in cancel button in window bar close all threads then the program
    // cause when I clicked cancel the window`s gone but the program still work
    public void end(Stage stage) {
        Platform.setImplicitExit(true);
        stage.setOnCloseRequest((ae) -> {
            Platform.exit();
            System.exit(0);
        });
    }

    @Override
    public void start(Stage stage) throws IOException {

        // set properties of window
        stage.setTitle("مقادير"); // window title
        stage.getIcons().add(new Image("/view/images/circle-logo.png")); // window icon
        
        //Load the view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mqadeer.fxml"));
        
        //Create the scene
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        
        //Set the Stage (send it to controller)        
        AnimationController newProjectController = loader.getController();
        newProjectController.setStage(stage);
        
        stage.show();       
        
        end(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args); 
    }
}
