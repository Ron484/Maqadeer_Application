package controller;

import static database.HibernateUtil.getSessionFactory;
import database.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Ekram
 */
public class ForgetPasswordController {

    @FXML
    private Label lblPassword;
    @FXML
    private Button verify_button;
    @FXML
    private Button back_button;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label passwordMsg;
    @FXML
    private PasswordField chkPasswordField;
    @FXML
    private Label chkpasswordMsg;

    int id;
    @FXML
    private Label lblMsg;

    @FXML
    private void btchange(ActionEvent event) throws IOException {

        if (passwordField.getText().isEmpty() && chkPasswordField.getText().isEmpty()) {
            passwordMsg.setText("يرجى تعبئة الحقل الفاضي");
            chkpasswordMsg.setText("يرجى تعبئة الحقل الفاضي");
            return;

        }
        else {

            if (passwordField.getText().isEmpty()) {
                passwordMsg.setText("يرجى تعبئة الحقل الفاضي");
                return;

            }
            else if (!passwordField.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
                passwordMsg.setText("على الاقل 8 حروف , حرف كبير وصغير ورقم");
                return;
            }
            else {

                passwordMsg.setText("");

            }
            if (chkPasswordField.getText().isEmpty()) {

                chkpasswordMsg.setText("يرجى تعبئة الحقل الفاضي");
                return;

            }
            else if (!chkPasswordField.getText().equals(passwordField.getText())) {
                chkpasswordMsg.setText("كلمة المرور غير متطابقه");
                return;
            }
            else {
                chkpasswordMsg.setText("");

            }

        }
        Session session = getSessionFactory().openSession();
        Query query = session.createSQLQuery("update user  set user_password=? where user_id=?").addEntity(User.class);
        query.setString(0, passwordField.getText());
        query.setString(1, String.valueOf(id));
        System.out.println(id);
        int i = query.executeUpdate();

        session.close();

        // check if the user exist
        if (i != 0) {
            System.out.println(i);
            lblMsg.setText("تم تحديث كلمة المرور");
            Navigation.changeScene(event, "/view/LogIn.fxml");

        }

    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Navigation.changeScene(event, "/view/LogIn.fxml");
    }

}
