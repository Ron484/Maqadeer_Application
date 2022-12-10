package controller;

import database.DB;
import database.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Ekram
 */
public class ProfileController implements Initializable {

    @FXML
    private Label lblUserName;
    @FXML
    private Label lblUserEmail;
    @FXML
    private Label lblRecipeCount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        User user = DB.getObject("FROM User WHERE id = ?", CurrentUser.id);
        lblUserName.setText(user.getName());
        lblUserEmail.setText(user.getEmail());
        
        Long numberOfRecipes = DB.count("SELECT COUNT(*) FROM Recipe WHERE userId = ?", CurrentUser.id);
        lblRecipeCount.setText(String.valueOf(numberOfRecipes));

    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Navigation.changeScene(event, "/view/HomePage.fxml");
    }

}
