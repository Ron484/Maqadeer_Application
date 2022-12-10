package controller;

import database.HibernateUtil;
import database.Recipe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Ekram
 */
public class HomePageController extends Navigation {

    List<Recipe> recipes = new ArrayList(0);
    private int recipeIndex = 0;
   
    @FXML
    private Label lblSuggestedRecipe;
    @FXML
    private VBox aletLogOut;
    
    
    private void suggestRecipe(String category) {
        // clear prevoius content of recipes array to add new ones
        recipes.clear();
        recipeIndex = 0;
        
        // get all recipes for curretn user that in spesific category 
        // Where all its ingredients are exists in foodstuff
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Query select = session.createSQLQuery(
            "SELECT * FROM recipe r WHERE user_id = ? AND r.category = ? AND EXISTS "
            + "(SELECT ingredient_name FROM ingredient i "
            + "WHERE r.recipe_id = i.recipe_id AND i.ingredient_name IN "
            + "(SELECT food_name FROM foodstuff f)) ").addEntity(Recipe.class);

        select.setParameter(0, CurrentUser.id);
        select.setParameter(1, category);
        recipes = select.list();
        session.close();
    }

    @FXML
    private void suggestDessert(ActionEvent event) {
        suggestRecipe(Category.DESSERT); 
        displayRecipe();
    }

    @FXML
    private void suggestDrink(ActionEvent event) {     
        suggestRecipe(Category.DRINKS);
        displayRecipe();
    }

    @FXML
    private void suggestMainCourse(ActionEvent event) {
        suggestRecipe(Category.MAIN_COURSE);
        displayRecipe();
    }
    
    @FXML
    private void suggestAppetizer(ActionEvent event) {
        suggestRecipe(Category.APPETIZER);
        displayRecipe();
    }   

    @FXML
    // get other recipe
    private void suggestOtherRecipe(ActionEvent event) {
        ++recipeIndex;
        if (recipeIndex >= recipes.size()) {
            lblSuggestedRecipe.setText("لا يوجد اقتراح آخر");
        }
        else { 
            displayRecipe();
        }
    }

    private void displayRecipe() {
        lblSuggestedRecipe.setText("");
        String suggestRecipe = recipes.isEmpty() 
                ? "لا توجد مكونات كافية في المطبخ لاقتراح طبخة" 
                : recipes.get(recipeIndex).getName();
        lblSuggestedRecipe.setText(suggestRecipe);
    }

    @FXML
    // it`s to pop an alert to confirm log out
    private void logOut(ActionEvent event) throws IOException {System.out.println("work");
        TranslateTransition goDown = new TranslateTransition();
        goDown.setNode(aletLogOut);
        goDown.setDuration(Duration.millis(400));
        goDown.setByY(500.0);
        goDown.play();
    }   

    @FXML
    private void noLogOut(ActionEvent event) {
        TranslateTransition goUp = new TranslateTransition();
        goUp.setNode(aletLogOut);
        goUp.setDuration(Duration.millis(400));
        goUp.setByY(-500.0);
        goUp.play();        
    }

    @FXML
    private void okLogOut(ActionEvent event) throws IOException {
        Navigation.changeScene(event, "/view/LogIn.fxml");        
    }

    @FXML
    private void viewRecipe(MouseEvent event) throws IOException {
        if (lblSuggestedRecipe.getText().equals("سيتم اقتراح طبخة بناء على المتوفر في المطبخ")
            || lblSuggestedRecipe.getText().equals("لا يوجد اقتراح آخر"))
            return;        
       
        Recipe recipe = recipes.get(recipeIndex);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewRecipe.fxml"));
       
        Parent viewRecipe = loader.load();
        Scene scene = new Scene(viewRecipe);
        scene.getStylesheets().add(Navigation.class.getResource("/view/style.css").toExternalForm());

        ViewRecipeController controller = loader.getController();
        controller.view(recipe.getMethod(), String.valueOf(recipe.getId()), recipe.getName());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }    

    @FXML
    private void nonUnderLine(MouseEvent event) {
        if (lblSuggestedRecipe.getText().equals("سيتم اقتراح طبخة بناء على المتوفر في المطبخ")
            || lblSuggestedRecipe.getText().equals("لا يوجد اقتراح آخر"))
            return;
        
        lblSuggestedRecipe.setUnderline(false);
    }

    @FXML
    private void underLine(MouseEvent event) {
        if (lblSuggestedRecipe.getText().equals("سيتم اقتراح طبخة بناء على المتوفر في المطبخ")
            || lblSuggestedRecipe.getText().equals("لا يوجد اقتراح آخر"))
            return;
        
        lblSuggestedRecipe.setUnderline(true);
    }

}
