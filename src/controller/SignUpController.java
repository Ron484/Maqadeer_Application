package controller;

import static controller.Email.generate_rand;
import database.HibernateUtil;
import static database.HibernateUtil.getSessionFactory;
import database.User;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
    private Label errorMsg;
    
    
    @FXML
    private Label lblLogPage;
     
    String rand=new String();
    
    User user = new User();
 
    /*
    
    public void showAlert(Alert.AlertType alertType,Window owner , String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
       // alert.setWidth(10);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
    
    
      
    
     User user = new User();
    
  
        
         
       
    
    
    

    private void nameAction(ActionEvent event) {
        
       // Session session = HibernateUtil.getSessionFactory().openSession();
        
        Window owner = userNameField.getScene().getWindow();
        if(userNameField.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR,owner,"empty field","Please enter a username");
        }
        else if(!userNameField.getText().matches("[A-Z|a-z][A-z|a-z|0-9]*")){
             showAlert(Alert.AlertType.ERROR,owner,"Error username","begins with a letter\n" +
              "no symbols");
        }
        
        else{
         user.setName(userNameField.getText());
            
        }
        return;
    }
    
    
    

    private void passwordAction(ActionEvent event) {
        
         Window owner = passwordField.getScene().getWindow();
        if(passwordField.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR,owner,"empty field","Please enter your Password");
        }
        else if(!passwordField.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")){
             showAlert(Alert.AlertType.ERROR,owner,"Error password"," - at least eight characters \n - at least one uppercase lette\n - at least one lowercase letter \n - at least one number \n - no symbols");
        }
        
        else{
         user.setPassword(passwordField.getText());
            
        }
        return;
    }
    
    private void invPasswordAction(ActionEvent event) {
         Window owner = invpasswordField.getScene().getWindow();
        if(invpasswordField.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR,owner,"empty field","Please re-enter your password");
        }
        else if(!invpasswordField.getText().equals(passwordField.getText())){
             showAlert(Alert.AlertType.ERROR,owner,"Error password","Not maching");
        }
        
        
        return;
        
        
        
        
    }

    private void emailAction(ActionEvent event) {
        
         Window owner = emailField.getScene().getWindow();
        if(emailField.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR,owner,"empty field","Please enter your eamil");
        }
 
        else if(!emailField.getText().matches("[a-z0-9]+@[a-z]+\\.[a-z]{2,3}")){
             showAlert(Alert.AlertType.ERROR,owner,"Error field","Please enter correct eamil");
        }
        else{
            user.setEmail(emailField.getText());
        }
        return;
        
        
    }
     */
    @FXML
    private void signUp(ActionEvent event) throws IOException {
Session session = getSessionFactory().openSession();
                    //session.beginTransaction();

         
        
        if(userNameField.getText().isEmpty()||passwordField.getText().isEmpty()||chkPasswordField.getText().isEmpty()||emailField.getText().isEmpty()){
            errorMsg.setText("يرجى تعبئة الحقول الفاضيه");
           return;
        }
        
       else {
            errorMsg.setText("");
            if(!userNameField.getText().matches("[A-Z|a-z][A-z|a-z|0-9]*")){
            nameMsg.setText("يبدأ بحرف , بدون رموز");
             return;
             
            }
            else {
             nameMsg.setText("");
             
             Query query = session.createQuery("from User u where u.name = :name ");
             query.setParameter("name", userNameField.getText());
             List<User> list = query.list();
             for(User u : list){
                 if(!u.getName().equals(null)){
                     nameMsg.setText("هناك شخص ما لديه هذا الاسم, جرب اسما مختلفا ");
                     
                     return;
                 }
            }
         
        }
            
             if(!emailField.getText().matches("[a-z0-9]+@[a-z]+\\.[a-z]{2,3}")){
             emailMsg.setText("من فضلك, ادخل بريد الكتروني صحيح");
             return;
                 }
             else{
             emailMsg.setText("");
             
             
               session.beginTransaction();
             Query query2 = session.createQuery("from User u where u.Email = :Email ");
             query2.setParameter("Email", emailField.getText());
             List<User> list2 = query2.list();
             for(User u : list2){
                 if(!u.getEmail().equals(null)){
                     emailMsg.setText("هذا الايميل مسجل مسبقا");
                     System.out.println("error");
                     session.getTransaction().commit();
                     return;
                 }
            }
             }
 
          if(!passwordField.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")){
             passwordMsg.setText("على الاقل 8 حروف , حرف كبير وصغير ورقم");
               return;
        }
          else{
              
            passwordMsg.setText("");
             
              }
    
             if(!chkPasswordField.getText().equals(passwordField.getText())){
             chkPasswordMsg.setText("كلمة المرور غير متطابقه");
              return;
                 }
             else{
                chkPasswordMsg.setText("");

        }
        
        
           
             }  
         session.close();
         
          
        /*
          user.setName(userNameField.getText());
          user.setEmail(emailField.getText());
          user.setPassword(passwordField.getText());
          
          int sId = (Integer) session.save(user);
          System.out.println("Done"+sId);
        
         
*/
        
        
        
        
        
         user.setName(userNameField.getText());
         user.setEmail(emailField.getText());
         user.setPassword(passwordField.getText());
          
       
          
          
         rand =String.join("",generate_rand());
         Email.sendTo(emailField.getText(), "مرحبا بك في تطبيقنا مقادير ", rand);
         
        
          
          
          
//           rand =String.join("",generate_rand());

        //Email.sendTo(emailField.getText(), "Done", rand);
       
     
     //  String rand =String.join("",generate_rand());

      //Email.sendTo("rnrnrn48@hotmail.com", "Done", s);
       
                  // session.close();
                  
           
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/VerifyAccount.fxml"));
    Parent verify=loader.load();
    Scene scene = new Scene(verify);
     
     VerifyAccountController controller = loader.getController();
      controller.rand2 = rand.toString();
      controller.user2 = user;
      
     
    Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
       
    stage.setScene(scene);
        
    stage.show();
        

    
    
   
     

    
    
   
       
    
       
        }

    
    @FXML
    private void goLogPage(MouseEvent event) throws IOException {
        Navigation.changeScene(event, "/view/LogIn.fxml");
    }

}
