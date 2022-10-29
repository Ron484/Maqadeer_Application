package main;

import database.*;
import controller.*;
import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        try {
            
            Parent root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
            Parent tryRoot = FXMLLoader.load(getClass().getResource("/view/AddingRecipe.fxml"));
            Parent sign = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));
            Parent log = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
            Parent v = FXMLLoader.load(getClass().getResource("/view/VerifyAccount.fxml"));
            
            
            Scene scene = new Scene(log);
            scene.getStylesheets().add(getClass().getResource("/view/style.css").toExternalForm());

            stage.setScene(scene);
            stage.show();
            
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        

        //Parent signUp = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));
        //Parent logIn = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
        //Parent homePage = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        //Scene scene = new Scene(logIn);
        //stage.setScene(scene);
        //stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}


//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//
//            Query query =  session.createSQLQuery("SELECT * FROM User WHERE user_name = :hi").addEntity(User.class);
//            query.setString("hi", name);
//            
//            User ekram = (User) query.uniqueResult();
//            System.out.println(ekram);
//
//        session.getTransaction().commit();
//        session.close();


//        FoodStuff f = new FoodStuff("egg", 1, "Box", "2022-10-14", "Ref", 1);
//        Recipe r = new Recipe("cake", "sweet", "Mix egg add suger", 1);
//        Ingredient i = new Ingredient("معكرونة", 3, "كاسات", 11);
//        NewRecipe nr = new NewRecipe("pancake", "twitter", "www.twitter.com", 1);
//        ShoppingList sh = new ShoppingList(1);
//        ShoppingListItem it = new ShoppingListItem("expire", 1); //????????????????????????????????????????