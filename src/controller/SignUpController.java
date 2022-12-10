package controller;

import static controller.Email.generate_rand;
import database.DB;
import static database.HibernateUtil.getSessionFactory;
import database.User;
import java.io.IOException;
import java.util.List;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Rehab
 */
public class SignUpController {

    @FXML
    private TextField userNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField chkPasswordField;

    @FXML
    private Label passwordMsg;

    @FXML
    private Label chkPasswordMsg;

    @FXML
    private Label nameMsg;
    @FXML
    private Label emailMsg;
    
    
    @FXML
    private Label lblLogPage;
     
    String rand=new String();
    
    User user = new User();
 
    @FXML
    private void signUp(ActionEvent event) throws IOException {
        Session session = getSessionFactory().openSession();

        if (userNameField.getText().isEmpty() && emailField.getText().isEmpty() 
            && passwordField.getText().isEmpty() && chkPasswordField.getText().isEmpty()) {
            nameMsg.setText("يرجى تعبئة الحقل ");
            emailMsg.setText("يرجى تعبئة الحقل ");
            passwordMsg.setText("يرجى تعبئة الحقل ");
            chkPasswordMsg.setText("يرجى تعبئة الحقل ");
            return;

        } else {
            if (userNameField.getText().isEmpty()) {
                nameMsg.setText("يرجى تعبئة الحقل ");
                return;

            } else if (!userNameField.getText().matches("[A-Z|a-z][A-z|a-z|0-9]*")) {
                nameMsg.setText("يبدأ بحرف , بدون رموز");
                return;

            } else {
                nameMsg.setText("");
                
//                if (DB.isExist("FROM User WHERE userName = ?", userNameField.getText())) {
//                    nameMsg.setText("هناك شخص ما لديه هذا الاسم, جرب اسما مختلفا ");
//                }

                Query query = session.createQuery("from User u where u.name = :name ");
                query.setParameter("name", userNameField.getText());
                List<User> list = query.list();
                for (User u : list) {
                    if (!u.getName().equals(null)) {
                        nameMsg.setText("هناك شخص ما لديه هذا الاسم, جرب اسما مختلفا ");

                        return;
                    }
                }

            }
            if (emailField.getText().isEmpty()) {
                emailMsg.setText("يرجى تعبئة الحقل ");
                return;

            } else if (!emailField.getText().matches("^(.+)@(.+)$")) {
                emailMsg.setText("من فضلك, ادخل بريد الكتروني صحيح");
                return;
            } else {
                emailMsg.setText("");

                session.beginTransaction();
                Query query2 = session.createQuery("from User u where u.Email = :Email ");
                query2.setParameter("Email", emailField.getText());
                List<User> list2 = query2.list();
                for (User u : list2) {
                    if (!u.getEmail().equals(null)) {
                        emailMsg.setText("هذا الايميل مسجل مسبقا");
                        System.out.println("error");
                        session.getTransaction().commit();
                        return;
                    }
                }
            }

            if (passwordField.getText().isEmpty()) {
                passwordMsg.setText("يرجى تعبئة الحقل ");
                return;

            } else if (!passwordField.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
                passwordMsg.setText("على الاقل 8 حروف , حرف كبير وصغير ورقم");
                return;
            } else {

                passwordMsg.setText("");

            }
            if (chkPasswordField.getText().isEmpty()) {

                chkPasswordMsg.setText("يرجى تعبئة الحقل الفاضي");
                return;

            } else if (!chkPasswordField.getText().equals(passwordField.getText())) {
                chkPasswordMsg.setText("كلمة المرور غير متطابقه");
                return;
            } else {
                chkPasswordMsg.setText("");

            }

        }
        session.close();

        user.setName(userNameField.getText());
        user.setEmail(emailField.getText());
        user.setPassword(passwordField.getText());

        rand = String.join("", generate_rand());
        Email.sendTo(emailField.getText(), "مرحبا بك في تطبيقنا مقادير ", rand);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/VerifyAccount.fxml"));
        Parent verify = loader.load();
        Scene scene = new Scene(verify);

        VerifyAccountController controller = loader.getController();
        controller.rand2 = rand.toString();
        controller.user2 = user;

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

        stage.show();

    }

    @FXML
    private void goLogPage(MouseEvent event) throws IOException {
        Navigation.changeScene(event, "/view/LogIn.fxml");
    }

}
