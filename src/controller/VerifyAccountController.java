/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static database.HibernateUtil.getSessionFactory;
import database.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Rehab
 */
public class VerifyAccountController {

    @FXML
    private Label lblPassword;
    @FXML
    private TextField text_field_verify;
    @FXML
    private Label error_label;
    @FXML
    private Button verify_button;
    @FXML
    private Button back_button;
    
    
    
    
    User user2 = new User();
    String rand2 =new String();
    int id;
    
    
    
        
        
        

  
    
    @FXML
    private void backAction(ActionEvent event) throws IOException {
        System.out.println(user2);
        if(id!=0){
        Navigation.changeScene(event, "/view/LogIn.fxml");       

        }
        else
        Navigation.changeScene(event, "/view/SignUp.fxml");       
    }

    @FXML
    private void verify(ActionEvent event) throws IOException {
    
         if(id!=0){
         
         if(text_field_verify.getText().equals(rand2)){
         error_label.setText("تم التحقق من الحساب , مرحبا بك");
         
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ForgetPassword.fxml"));
            Parent pass = loader.load();
            Scene scene = new Scene(pass);

         
           ForgetPasswordController controller = loader.getController();
           controller.id = id;
           
            Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
           stage.setScene(scene);
         stage.show();
          
         }
       else{
         error_label.setText("الكود غير متطابق حاول مره اخرى");
      return;
        }
         
         }
         else{
         Parent home = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
         Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
         Scene scene = new Scene(home);
       
        

         Session session2 = getSessionFactory().openSession();
         session2.beginTransaction();
          
         if(text_field_verify.getText().equals(rand2)){
         error_label.setText("تم تأكيد الحساب , مرحبا بك");
              
         
         session2.save(user2);
         
         Query g = session2.createQuery("SELECT id FROM User WHERE id = LAST_INSERT_ID()");
         int userId = (Integer) g.uniqueResult();
         CurrentUser.id = userId; 
         
         
         session2.getTransaction().commit();

        
            
        System.out.println("Done");
         

        
     
       }
       else{
      error_label.setText("الكود غير متطابق حاول مره اخرى");
      return;
        }
        session2.close();
        stage.setScene(scene);
        
        stage.show();
         }
        
    }
    
    
        
    }

