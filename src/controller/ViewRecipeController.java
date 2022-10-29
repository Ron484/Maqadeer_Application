/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static database.HibernateUtil.*;
import database.Ingredient;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author user
 */
public class ViewRecipeController {
    @FXML
    private TextArea ingredient_text;
    @FXML
    private TextArea method_text;
    @FXML
    private Button btAdd;
    
    
    
        public void view(String recipe_method, String recipe_id ){
            
             Session session = getSessionFactory().openSession();
         
             session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from ingredient where recipe_id=?").addEntity(Ingredient.class);
            query.setString(0, recipe_id);
           
            List <Ingredient> list = query.list();
            
            for(int i=0;i<list.size();i++){
                
            
            ingredient_text.setText(ingredient_text.getText()+"\n"+list.get(i).getName()+" "+(int)list.get(i).getQuantity()+" "+list.get(i).getQuantityUnit());
            }
            method_text.setText(recipe_method);
            
            session.getTransaction().commit();
            session.close();

        }

    @FXML
    private void btBack(ActionEvent event) throws IOException {
          Navigation.changeScene(event,"/view/MyRecipes.fxml"); 
    }
             
 }
        
       
    
    
    
    

