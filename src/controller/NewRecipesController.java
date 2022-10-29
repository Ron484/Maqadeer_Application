package controller;

import static database.HibernateUtil.getSessionFactory;
import database.NewRecipe;
import java.net.URL;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Ekram
 */
public class NewRecipesController extends Navigation {

    @FXML
    private AnchorPane panRoot;
    @FXML
    private Label lbTitel;
    @FXML
    private Button btdrinks;
    @FXML
    private Button btdessert;
    @FXML
    private Button btappetizers;
    @FXML
    private Button btmaincourse;
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
    private Label lbDrink;
    @FXML
    private Label lbDessert;
    @FXML
    private Label lbAppetizer;
    @FXML
    private Label lbMain;

    @FXML
    private void show_drinks_page(ActionEvent event) {
        Session session = getSessionFactory().openSession();
          try {
            session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from new_recipe where section=?").addEntity(NewRecipe.class);
            query.setString(0, lbDrink.getText());
           
            List <NewRecipe> recipe_list = query.list();
            
            session.getTransaction().commit();
            session.close();


                   
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/DisplayRecipes.fxml"));
            Parent DisplayRecipe=loader.load();
            Scene scene = new Scene(DisplayRecipe);
    
     
          DisplayRecipesController controller = loader.getController();
           
           controller.view(recipe_list,lbDrink.getText());
    
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
    private void show_dessert_page(ActionEvent event) {
        Session session = getSessionFactory().openSession();
          try {
            session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from new_recipe where section=?").addEntity(NewRecipe.class);
            query.setString(0, lbDessert.getText());
           
            List <NewRecipe> recipe_list = query.list();
            
            session.getTransaction().commit();
            session.close();


                   
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/DisplayRecipes.fxml"));
            Parent DisplayRecipe=loader.load();
            Scene scene = new Scene(DisplayRecipe);
    
     
          DisplayRecipesController controller = loader.getController();
           
           controller.view(recipe_list,lbDessert.getText());
    
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
    private void show_appetizers_page(ActionEvent event) {
        Session session = getSessionFactory().openSession();
          try {
            session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from new_recipe where section=?").addEntity(NewRecipe.class);
            query.setString(0, lbAppetizer.getText());
           
            List <NewRecipe> recipe_list = query.list();
            
            session.getTransaction().commit();
            session.close();


                   
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/DisplayRecipes.fxml"));
            Parent DisplayRecipe=loader.load();
            Scene scene = new Scene(DisplayRecipe);
    
     
          DisplayRecipesController controller = loader.getController();
           
          controller.view(recipe_list,lbAppetizer.getText());
    
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
    private void show_main_course_page(ActionEvent event) {
        Session session = getSessionFactory().openSession();
          try {
            session.beginTransaction();
            
            Query query =  session.createSQLQuery("SELECT * from new_recipe where section=?").addEntity(NewRecipe.class);
            query.setString(0, lbMain.getText());
           
            List <NewRecipe> recipe_list = query.list();
            
            session.getTransaction().commit();
            session.close();


                   
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/DisplayRecipes.fxml"));
            Parent DisplayRecipe=loader.load();
            Scene scene = new Scene(DisplayRecipe);
    
     
          DisplayRecipesController controller = loader.getController();
           
           
           controller.view(recipe_list,lbMain.getText());
    
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

    

