package controller;

import static database.HibernateUtil.*;
import database.Ingredient;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Rehab
 */
public class ViewRecipeController {

    @FXML
    private Button btAdd1;
    @FXML
    private Label recipeName;
    @FXML
    private Label lblIngredients;
    @FXML
    private Label lblMethod;

    boolean isFromHomePage;

    public void view(String recipe_method, String recipe_id, String recipe_name, boolean fromHome) {
       
        // to know when go back to which page 
        isFromHomePage = fromHome;
        
        Session session = getSessionFactory().openSession();

        session.beginTransaction();

        Query query = session.createSQLQuery("SELECT * from ingredient where recipe_id=?").addEntity(Ingredient.class);
        query.setString(0, recipe_id);

        List<Ingredient> list = query.list();

        for (int i = 0; i < list.size(); i++) {

            lblIngredients.setText(lblIngredients.getText() + "\n" + list.get(i).getName() + " " + (int) list.get(i).getQuantity() + " " + list.get(i).getQuantityUnit());
        }
        lblMethod.setText(recipe_method);
        recipeName.setText(recipe_name);
        session.getTransaction().commit();
        session.close();

    }

    @FXML
    private void btBack(ActionEvent event) throws IOException {
       
        if (isFromHomePage) {
            Navigation.changeScene(event, "/view/HomePage.fxml");
        }
        else {
            Navigation.changeScene(event, "/view/MyRecipes.fxml");
        }
    }
}
