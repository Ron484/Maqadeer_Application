package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Ekram
 */
public class ForgetPasswordController {

    @FXML
    private Label lblPassword;
    @FXML
    private Label error_label;
    @FXML
    private Button verify_button;
    @FXML
    private Button back_button;

    @FXML
    private void btchange(ActionEvent event) {
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Navigation.changeScene(event, "/view/LogIn.fxml");
    }
    
}
