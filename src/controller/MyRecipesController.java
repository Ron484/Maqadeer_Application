package controller;

import static database.HibernateUtil.getSessionFactory;
import database.Recipe;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author 
 */
public class MyRecipesController extends Navigation{

    @FXML
    private AnchorPane panRoot;
    @FXML
    private Label lbTitel;
    @FXML
    private HBox hbSearch;
    @FXML
    private Button btAdd;
    @FXML
    private TextField tfSearch;
    @FXML
    private ImageView imgSearch;
    @FXML
    private ToggleGroup tgType;
    
    

    @FXML
    private ToggleButton btShopListPage;
    @FXML
    private ToggleGroup navPage;
    @FXML
    private ToggleButton btNewRecipePage;
    @FXML
    private ToggleButton btHomePage;
    @FXML
    private ToggleButton btRecipePage;
    @FXML
    private ToggleButton btFoodPage;
    @FXML
    private ToggleButton btdessert;
    @FXML
    private ToggleButton btDrink;
    @FXML
    private ToggleButton btAppetizer;
    @FXML
    private ToggleButton btMain;
    @FXML
    private ToggleButton btAll;
    @FXML
    private Label lbresult3;
    @FXML
    private Label lbresult4;
    @FXML
    private Label lbresult1;
    @FXML
    private Label lbresult2;
    private StackPane invisibleStackPane;


    @FXML
    private void goToAddingPage(ActionEvent event) throws IOException {        
        Navigation.changeScene(event, "/view/AddingRecipe.fxml");         
    }
    
    private void searchResult(String result){
        if(!result.isEmpty()){
          if(lbresult1.getText().isEmpty()){
            lbresult1.setText(result);}
            else if(lbresult2.getText().isEmpty()){
            lbresult2.setText(result); 
            }
            else if(lbresult3.getText().isEmpty()){
            lbresult3.setText(result);   
            }
            else if(lbresult4.getText().isEmpty()){
            lbresult4.setText(result); 
              }
            else{
            lbresult1.setText(result) ; 
            lbresult2.setText("") ; 
            lbresult3.setText("") ; 
            lbresult4.setText("") ;    
            }
        }
        else{
             
      Rectangle rec = new Rectangle();
      rec.setArcWidth(5);
      rec.setArcHeight(5);
      rec.setFill(Color.WHITE);
      
      Label lbMsg = new Label();
      lbMsg.setText("نتائج البحث غير متوفرة");
      lbMsg.setTextFill(Color.GRAY);
      
     invisibleStackPane.getChildren().addAll(rec,lbMsg);
      
     
      panRoot.getChildren().add(invisibleStackPane);
    
            
        }
            
        
    }

    @FXML
    private void searchAction(ActionEvent event) {
           Session session = getSessionFactory().openSession();
        if(btAll.isSelected()){
           try {
   
            session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from Recipe where recipe_name=?").addEntity(Recipe.class);
            query.setString(0, tfSearch.getText());
           
            Recipe recipe = (Recipe) query.uniqueResult();
            searchResult(recipe.getName());
          
            session.getTransaction().commit();
            
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        }
        else if( btMain.isSelected()){
            try {
   
        
            session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from Recipe where recipe_name=? and category=?").addEntity(Recipe.class);
            query.setString(0, tfSearch.getText());
            query.setString(1,btMain.getText());
           
            Recipe recipe = (Recipe) query.uniqueResult();
            searchResult(recipe.getName());
            
            session.getTransaction().commit();
         
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        }
        else if( btAppetizer.isSelected()){
            try {
   
           
            session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from Recipe where recipe_name=? and category=?").addEntity(Recipe.class);
            query.setString(0, tfSearch.getText());
            query.setString(1, btAppetizer.getText());
           
            Recipe recipe = (Recipe) query.uniqueResult();
            searchResult(recipe.getName());
            
            session.getTransaction().commit();
         
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        }
        else if( btDrink.isSelected()){
            try {
   
      
            session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from Recipe where recipe_name=? and category=?").addEntity(Recipe.class);
            query.setString(0, tfSearch.getText());
            query.setString(1,  btDrink.getText());
           
            Recipe recipe = (Recipe) query.uniqueResult();
            searchResult(recipe.getName());
            
            session.getTransaction().commit();
           
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        }
        else if(btdessert.isSelected()){
            try {
   
           
            session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from Recipe where recipe_name=? and category=?").addEntity(Recipe.class);
            query.setString(0, tfSearch.getText());
            query.setString(1, btdessert.getText());
           
            Recipe recipe = (Recipe) query.uniqueResult();
            searchResult(recipe.getName());
            
            session.getTransaction().commit();
           
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        }
            
        else{
            System.out.println("اختر التصنيف اولا");
        } 
         session.close();
        }



   

    @FXML
    private void ViewAction1(MouseEvent event) {
        Session session = getSessionFactory().openSession();
          try {
            session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from Recipe where recipe_name=?").addEntity(Recipe.class);
            query.setString(0, lbresult1.getText());
           
            Recipe recipe = (Recipe) query.uniqueResult();
            
            session.getTransaction().commit();
            session.close();


                   
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ViewRecipe.fxml"));
            Parent viewRecipe=loader.load();
            Scene scene = new Scene(viewRecipe);
    
     
          ViewRecipeController controller = loader.getController();
              //   controller.recipe_method=recipe.getMethod();
                // controller.recipe_id=String.valueOf(recipe.getId());
           controller.view(recipe.getMethod(), String.valueOf(recipe.getId()));
    
           Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
       
           stage.setScene(scene);
        
           stage.show();
        
            
       }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
       
    }


    @FXML
    private void ViewAction2(MouseEvent event) {
         Session session = getSessionFactory().openSession();
          try {
            session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from Recipe where recipe_name=?").addEntity(Recipe.class);
            query.setString(0, lbresult2.getText());
           
            Recipe recipe = (Recipe) query.uniqueResult();
            
            session.getTransaction().commit();
            session.close();


                   
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ViewRecipe.fxml"));
            Parent viewRecipe=loader.load();
            Scene scene = new Scene(viewRecipe);
    
     
          ViewRecipeController controller = loader.getController();
              //   controller.recipe_method=recipe.getMethod();
                // controller.recipe_id=String.valueOf(recipe.getId());
           controller.view(recipe.getMethod(), String.valueOf(recipe.getId()));
    
           Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
       
           stage.setScene(scene);
        
           stage.show();
        
            
       }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
       
    }

    

    @FXML
    private void ViewAction3(MouseEvent event) {
         Session session = getSessionFactory().openSession();
          try {
            session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from Recipe where recipe_name=?").addEntity(Recipe.class);
            query.setString(0, lbresult3.getText());
           
            Recipe recipe = (Recipe) query.uniqueResult();
            
            session.getTransaction().commit();
            session.close();


                   
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ViewRecipe.fxml"));
            Parent viewRecipe=loader.load();
            Scene scene = new Scene(viewRecipe);
    
     
          ViewRecipeController controller = loader.getController();
              //   controller.recipe_method=recipe.getMethod();
                // controller.recipe_id=String.valueOf(recipe.getId());
           controller.view(recipe.getMethod(), String.valueOf(recipe.getId()));
    
           Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
       
           stage.setScene(scene);
        
           stage.show();
        
            
       }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
       
    }

    
     @FXML
    private void ViewAction4(MouseEvent event) {
         Session session = getSessionFactory().openSession();
          try {
            session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from Recipe where recipe_name=?").addEntity(Recipe.class);
            query.setString(0, lbresult4.getText());
           
            Recipe recipe = (Recipe) query.uniqueResult();
            
            session.getTransaction().commit();
            session.close();


                   
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ViewRecipe.fxml"));
            Parent viewRecipe=loader.load();
            Scene scene = new Scene(viewRecipe);
    
     
          ViewRecipeController controller = loader.getController();
              //   controller.recipe_method=recipe.getMethod();
                // controller.recipe_id=String.valueOf(recipe.getId());
           controller.view(recipe.getMethod(), String.valueOf(recipe.getId()));
    
           Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
       
           stage.setScene(scene);
        
           stage.show();
        
            
       }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
       
    }

    

}


