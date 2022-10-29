package controller;

import database.HibernateUtil;
import database.Recipe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author
 */
public class HomePageController extends Navigation {

    List<Recipe> recipes = new ArrayList(0);
    private int recipeIndex = 0;

    private TextArea taSuggestedRecipe;
    @FXML
    private Button btAdd;
    @FXML
    private ToggleButton btShopListPage1;
    @FXML
    private ToggleGroup navPage1;
    @FXML
    private ToggleButton btNewRecipePage1;
    @FXML
    private ToggleButton btHomePage1;
    @FXML
    private ToggleButton btRecipePage1;
    @FXML
    private ToggleButton btFoodPage1;

    @FXML
    private void suggestDessert(ActionEvent event) {
        recipes.clear();
        recipeIndex = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        recipes = session.createSQLQuery(
                "SELECT * FROM recipe r WHERE r.category = \"حلا\" AND EXISTS( "
                + "SELECT ingredient_name FROM ingredient i WHERE r.recipe_id = i.recipe_id AND i.ingredient_name IN ("
                + "SELECT food_name FROM foodstuff f)) ").addEntity(Recipe.class).list();

        session.close();
        displayRecipe();
    }

    @FXML
    private void suggestDrink(ActionEvent event) {
        recipes.clear();
        recipeIndex = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        recipes = session.createSQLQuery(
                "SELECT * FROM recipe r WHERE r.category = \"مشروبات\" AND EXISTS( "
                + "SELECT ingredient_name FROM ingredient i WHERE r.recipe_id = i.recipe_id AND i.ingredient_name IN ("
                + "SELECT food_name FROM foodstuff f)) ").addEntity(Recipe.class).list();

        session.close();
        displayRecipe();
    }

    @FXML
    private void suggestMainCourse(ActionEvent event) {
        recipeIndex = 0;
        recipes.clear();
        Session session = HibernateUtil.getSessionFactory().openSession();
        recipes = session.createSQLQuery(
                "SELECT * FROM recipe r WHERE r.category = \"وجبة رئيسة\" AND EXISTS( "
                + "SELECT ingredient_name FROM ingredient i WHERE r.recipe_id = i.recipe_id AND i.ingredient_name IN ("
                + "SELECT food_name FROM foodstuff f)) ").addEntity(Recipe.class).list();

        session.close();
        displayRecipe();
    }

    @FXML
    private void suggestOtherRecipe(ActionEvent event) {
        ++recipeIndex;
        if (recipeIndex >= recipes.size()) {
            taSuggestedRecipe.setText("There are`t other choice");
        }
        else {
            displayRecipe();
        }
    }

    private void displayRecipe() {
        taSuggestedRecipe.clear();
        String suggestRecipe = recipes.isEmpty() ? "Sorry" : recipes.get(recipeIndex).getName();
        taSuggestedRecipe.setText(suggestRecipe);
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        Navigation.changeScene(event, "/view/LogIn.fxml");
    }
}
