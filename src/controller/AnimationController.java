package controller;

import database.HibernateUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Fadyah and Afnan
 */
public class AnimationController implements Initializable {

    @FXML
    private ImageView wisker;
    @FXML
    private ImageView maki;

    @FXML
    private AnchorPane logo;

    private Stage stage;

    boolean is = false;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.getTransaction().commit();
        session.close();
        playSound();

        // The logo is become better
        ScaleTransition tranLogo1 = new ScaleTransition();
        tranLogo1.setDelay(Duration.seconds(1.0));
        tranLogo1.setNode(maki);
        tranLogo1.setDuration(Duration.millis(2200));
        tranLogo1.setByY(-0.2);
        tranLogo1.setByX(-0.2);
        tranLogo1.play();

        // The wisker drop down
        TranslateTransition tranWisk = new TranslateTransition();
        tranWisk.setDelay(Duration.seconds(4.0));
        tranWisk.setNode(wisker);
        tranWisk.setDuration(Duration.millis(1300));
        tranWisk.setByY(370.0);
        tranWisk.setAutoReverse(true);
        tranWisk.play();

        // The Wisker is turning 
        TranslateTransition tranWisk2 = new TranslateTransition();
        tranWisk2.setDelay(Duration.seconds(5.6));
        tranWisk2.setDuration(Duration.millis(200));
        tranWisk2.setNode(wisker);
        tranWisk2.setCycleCount(TranslateTransition.INDEFINITE);
        tranWisk2.setByX(10.5);
        tranWisk2.setAutoReverse(true);
        tranWisk2.play();

        // The wisker go up
        TranslateTransition tranWisk4 = new TranslateTransition();
        tranWisk4.setDelay(Duration.seconds(6.9));
        tranWisk4.setDuration(Duration.millis(1200));
        tranWisk4.setNode(wisker);
        tranWisk4.setFromY(370);
        tranWisk4.setToY(0);
        tranWisk4.play();

        // The wisker go down
        TranslateTransition tranLogo2 = new TranslateTransition();
        tranLogo2.pause();
        tranLogo2.setDelay(Duration.millis(6950.0));
        tranLogo2.setNode(maki);
        tranLogo2.setDuration(Duration.millis(1800));
        tranLogo2.setFromY(tranLogo2.getByY());
        tranLogo2.setToY(390);
        tranLogo2.play();

        // when animtaion finished go to log-in page
        tranLogo2.setOnFinished(e -> {
            goLogInPage();
        });
    }

    public static void playSound() {
        String path = "voice.mp3";
        Media letterSound = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
        mediaPlayer.play();
    }

    private void goLogInPage() {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/LogIn.fxml")));
            scene.getStylesheets().add(Navigation.class.getResource("/view/style.css").toExternalForm());
            stage.setScene(scene);
        }
        catch (IOException ex) {
            Logger.getLogger(AnimationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
