package controller;

import database.*;
import database.HibernateUtil;
import static database.HibernateUtil.getSessionFactory;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author huawei
 */
public class LogInController {

    @FXML
    private Button btSubmit;

    @FXML
    private Label lblName;

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
    @FXML
    private Label lblErrorMsg1;

     
   
   
  
    @FXML
    private void goSignUpPage(MouseEvent event) {
          try {
            FXMLLoader.load(getClass().getResource("singUp.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


    @FXML
    private void logIn(ActionEvent event) throws IOException {
          /*   
        User user =new User();
        if(tfName.getText().isEmpty()||tflPassword.getText().isEmpty()){
            lblErrorMsg1.setText("املأ جميع الحقول");
            return;
        }
        else{
          
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            if(!tfName.getText().contains("@")){
             Query  query =  session.createSQLQuery("SELECT * from User where user_name=? ").addEntity(User.class);
             
            query.setString(0,tfName.getText());
            user = (User) query.uniqueResult();
               
            }
            else{
            Query  query =  session.createSQLQuery("SELECT * from User where user_email=? ").addEntity(User.class);
            
            query.setString(0,tfName.getText());
            user = (User) query.uniqueResult();
              
            
            }
             session.getTransaction().commit();
              
             
                
            if(user.equals(null)){
                lblErrorMsg1.setText("هذا المستخدم غير مسجل مسبقا");
                return;
            
            }
            else{
           
            Query query =  session.createSQLQuery("SELECT * from User where user_email=? and user_password=? ").addEntity(User.class);
             query.setString(0,tfName.getText());
             query.setString(1,tflPassword.getText());
             user = (User) query.uniqueResult();
             if(user.equals(null)){
               lblErrorMsg1.setText("تحقق من صحة كلمة المرور");
              return;
                 
             }
             else{ 
            
            
            
            
            session.close();*/
            Navigation.changeScene(event,"/view/HomePage.fxml");
            // }
             
               // }
           // }
    }   

    @FXML
    private void goForgetPasswordPage(MouseEvent event) {
    }

}
