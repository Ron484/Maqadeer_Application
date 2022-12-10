package controller;

import static controller.Email.generate_rand;
import database.*;
import static database.HibernateUtil.getSessionFactory;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author huawei
 */
public class LogInController {

    @FXML
    private Button btSubmit;

    @FXML
    private Label lblPassword;

    @FXML
    private TextField tfName;

    @FXML
    private Label lblForgetPassword;

    @FXML
    private PasswordField tflPassword;
    @FXML
    private Label btlSignUp;
    private Label lblErrorMsg1;
    @FXML
    private Label lblRecipeNameMsg1;
    @FXML
    private Label lblUserMsg;

    User user;
    
    @FXML
    private void goSignUpPage(MouseEvent event) throws IOException {
        Navigation.changeScene(event, "/view/SignUp.fxml");
    }

    @FXML
    private void logIn(ActionEvent event) throws IOException, InterruptedException {

        if (tfName.getText().isEmpty() && tflPassword.getText().isEmpty()) {
            lblUserMsg.setText("املأ جميع الحقول");
            lblPassword.setText("املأ جميع الحقول");
            return;
        }
        lblUserMsg.setText("");

        // get the user from database by his email or user name
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        if (!tfName.getText().contains("@")) {
            Query query = session.createSQLQuery("SELECT * from User where user_name=? ").addEntity(User.class);
            query.setString(0, tfName.getText());
            user = (User) query.uniqueResult();
        }
        else {
            Query query = session.createSQLQuery("SELECT * from User where user_email=? ").addEntity(User.class);
            query.setString(0, tfName.getText());
            user = (User) query.uniqueResult();
        }
        session.getTransaction().commit();
        session.close();

        // check if the user exist
        if (user == null) {
            lblUserMsg.setText("هذا المستخدم غير مسجل مسبقا");
            return;
        }
        lblUserMsg.setText("");

        // check if password match        
        if (tflPassword.getText().isEmpty()) {
            lblPassword.setText("املأ جميع الحقول");
        }
        else {
            String userPassword = user.getPassword();
            String enterdPassword = tflPassword.getText();
            if (!userPassword.equals(enterdPassword)) {
                lblPassword.setText("تحقق من صحة كلمة المرور");
                return;
            }
            lblPassword.setText("");
            // the user is right he can log to home page
            CurrentUser.id = user.getId();
            Navigation.changeScene(event, "/view/HomePage.fxml");
        }
    }

    @FXML
    private void goForgetPasswordPage(MouseEvent event) throws IOException {
        
        if (!tfName.getText().contains("@")) {
            lblUserMsg.setText("من فضلك ادخل ايميلك اولا ");
            return;
        }
        else {
            lblUserMsg.setText("");

            Session session = getSessionFactory().openSession();
            Query query = session.createSQLQuery("SELECT * from User where user_email=? ").addEntity(User.class);
            query.setString(0, tfName.getText());
            user = (User) query.uniqueResult();
            session.close();

            // check if the user exist
            if (user == null) {
                lblUserMsg.setText("هذا المستخدم غير مسجل مسبقا");
                return;
            }
            lblUserMsg.setText("");

            //send email to user
            String rand = String.join("", generate_rand());
            Email.sendTo(tfName.getText(), "تم التأكيد يمكنك تغيير كلمة المرور", rand);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/VerifyAccount.fxml"));
            Parent verify = loader.load();
            Scene scene = new Scene(verify);

            VerifyAccountController controller = loader.getController();
            controller.rand2 = rand.toString();
            controller.id = user.getId();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();
        }
    }

}
