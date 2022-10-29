package controller;

import database.HibernateUtil;
import database.Ingredient;
import database.Recipe;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Ekram
 */
public class AddingRecipeController extends Navigation {

    Recipe recipeToAdd = new Recipe();
    ArrayList<Ingredient> ingredients = new ArrayList();

    private TextField tfRecipeMethod;
    @FXML
    private TextField tfRecipeName;
    @FXML
    private TextField tfIngredientUnit;
    @FXML
    private TextField tfIngredientQuantity;
    @FXML
    private TextField tfIngredientName;
    @FXML
    private Label lblRecipeNameMsg;
    @FXML
    private Label lblIngredientMsg;
    @FXML
    private Button btAdd1;
    @FXML
    private Button btAdd;

    @FXML
    private void addRecipe(ActionEvent event) {
        
        if (recipeToAdd.getName().isEmpty() && ingredients.isEmpty()){
            lblRecipeNameMsg.setText("يجب عليك إدخال اسم الطبخة");
            lblIngredientMsg.setText("يجب عليك إضافة مكونات للطبخة");
            return;
        }
        
        recipeToAdd.setCategory("");
        recipeToAdd.setMethod(tfRecipeMethod.getText());
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();       
        int recipeId = (Integer) session.save(recipeToAdd);
        for(Ingredient ingredient: ingredients) {
            ingredient.setRecipeId(recipeId);
            session.save(ingredient);
        }
        
        tx.commit();
        session.close();       
    }

    @FXML
    private void checkDuplicateRecipe(ActionEvent event) {
        
        String newRecipeName = tfRecipeName.getText();
        if (newRecipeName.isEmpty()){
            lblRecipeNameMsg.setText("يجب عليك إدخال اسم الطبخة");
            return;
        }

        // check duplicated
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("FROM Recipe WHERE name = ?").setString(0, newRecipeName);
        Recipe recipe = (Recipe) query.uniqueResult();

        if (recipe != null) {
            lblRecipeNameMsg.setText("الطبخة موجودة مسبقا");
        }
        
        // It`s valid name
        recipeToAdd.setName(newRecipeName);
        
        
        tx.commit();
        session.close();
    }

    @FXML
    private void addIngredient(ActionEvent event) {
        
        String name = tfIngredientName.getText();
        String quantity = tfIngredientQuantity.getText();
        String unit = tfIngredientUnit.getText();
        
        if (name.isEmpty() && quantity.isEmpty() && unit.isEmpty()) {
            lblIngredientMsg.setText("يجب عليك إكمال جميع الحقول الخاصة بالمكون");
        }
        else if (quantity.matches("")) {
            lblIngredientMsg.setText("يجب أن يكون المقدار رقما");
        }
        else if (ingredients.contains(name)){ //???????????????????????????????
            lblIngredientMsg.setText("هذا المكون تم إضافته مسبقا");
        }
        else {
            ingredients.add(new Ingredient(name, new Integer(quantity), unit));
            lblIngredientMsg.setText("تم إضافة " + name + " بنجاح ");
        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Navigation.changeScene(event, "/view/MyRecipes.fxml");       
    }
}
