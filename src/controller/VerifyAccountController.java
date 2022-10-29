/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

//import static controller.Email.generate_rand;
import static controller.Email.generate_rand;
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
 * @author user
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
  
    
    
    
        
        
        

  
    
    @FXML
    private void backAction(ActionEvent event) throws IOException {
        Navigation.changeScene(event, "/view/SignUp.fxml");       
    }

    @FXML
    private void verify(ActionEvent event) throws IOException {
        // after check the number navigate to home page
        
         
         Parent home = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
         Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
         Scene scene = new Scene(home);
       
        

         Session session2 = getSessionFactory().openSession();
         session2.beginTransaction();
          
         if(text_field_verify.getText().equals(rand2)){
         error_label.setText("تم تأكيد الحساب , مرحبا بك");
              
         
         session2.save(user2);
         session2.getTransaction().commit();

        
            
        System.out.println("Done");
         

        
     
       }
       else{
      error_label.setText("الكود غير متطابق حاول مره اخرى");
        }
        session2.close();
        stage.setScene(scene);
        
        stage.show();
        
        //Navigation.changeScene(event, "/view/HomePage.fxml");
        
    }
    
    
        
    }

