package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// this class to navigate between pages
public class Navigation {

    // to change scene
    public static void changeScene(Event event, String sceneURL) throws IOException {

        Parent root = FXMLLoader.load(Navigation.class.getResource(sceneURL));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Navigation.class.getResource("/view/style.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goHomePage(ActionEvent event) throws IOException {
        changeScene(event, "/view/HomePage.fxml");
    }

    @FXML
    public void goMyRecipePage(ActionEvent event) throws IOException {
        changeScene(event, "/view/MyRecipes.fxml");
    }

    @FXML
    public void goFoodPage(ActionEvent event) throws IOException {
        changeScene(event, "/view/MyKitchen.fxml");
    }

    @FXML
    public void goShopListPage(ActionEvent event) throws IOException {
        changeScene(event, "/view/ShoppingList.fxml");
    }

    @FXML
    public void goNewRecipesPage(ActionEvent event) throws IOException {
        changeScene(event, "/view/NewRecipes.fxml");
    }
}
