package controller;

import database.DB;
import database.HibernateUtil;
import database.Ingredient;
import database.Recipe;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
    ArrayList<Ingredient> ingredients = new ArrayList<>();

    @FXML
    private Label lblCategory;
    
    @FXML
    private TextField tfRecipeName;
    
    @FXML
    private TextField tfIngredientName; 
    @FXML
    private TextField tfIngredientQuantity;
    @FXML
    private TextField tfIngredientUnit;
    
    @FXML
    private TextArea tfMethod;
    
    @FXML
    private Label lblRecipeNameMsg;    
    @FXML
    private Label lblIngredientMsg;
    @FXML
    private Label lblCategoryMsg;
    @FXML
    private Label lblSuccess;
   
       
    
    private boolean validateRecipeName() {

        String recipeName = tfRecipeName.getText();

        // the user didn`t enter name of the recipe
        if (recipeName.isEmpty()) {
            lblRecipeNameMsg.setText("يجب عليك إدخال اسم الطبخة");
            return false;
        }

        // the recipe name is duplicated          
        if (DB.isExist("FROM Recipe WHERE userId = ? AND name = ?",
                CurrentUser.id, recipeName)) {
            lblRecipeNameMsg.setText("الطبخة موجودة مسبقا");
            return false;
        }

        // the recipe name is valid 
        recipeToAdd.setName(recipeName);
        return true;
    }

    boolean validateAllFeilds() {

        // check all fields has compeleted
        // it will be false if one of field is empty
        boolean isComplete = true;

        // recipe name field
        if (!validateRecipeName()) {
            isComplete = false;
        }

        // ingredient fields: the use has entered them in the array
        if (ingredients.isEmpty()) {
            lblIngredientMsg.setTextFill(Color.rgb(188, 5, 5));
            lblIngredientMsg.setText("يجب عليك إضافة مكونات للطبخة");
            isComplete = false;
        }

        // check recipe category
        if (recipeToAdd.getCategory() == null) {
            lblCategoryMsg.setText("اختر تصنيف للطبخة");
            isComplete = false;
        }

        // OPTINAL: the user may entered or not
        if (tfMethod.getText() != null) {
            recipeToAdd.setMethod(tfMethod.getText());
        }

        return isComplete;
    }

    @FXML    
    // Action Event in (Add Recipe) button     
    private void addRecipe(ActionEvent event) throws IOException, InterruptedException {

        // all attribute of recipe has filled and assigned
        if (!validateAllFeilds()) {
            return;
        }

        // assign the user id to current one
        recipeToAdd.setUserId(CurrentUser.id);

        // add the recipe
        DB.save(recipeToAdd);
        
        // get Id for last recipe insert for its ingredients
        Session session = HibernateUtil.getSessionFactory().openSession();        
        Transaction t = session.beginTransaction();       
        Query g = session.createQuery("SELECT id FROM Recipe WHERE id = LAST_INSERT_ID()");
        int recipeId = (Integer) g.uniqueResult();       
        t.commit();
        session.close();
        
        // add ingredients of the recipe
        for (Ingredient ingredient : ingredients) {            
            ingredient.setRecipeId(recipeId);
            DB.save(ingredient);   
        }
        
        // clear all fields and labels
        clearTextFields(tfRecipeName, tfIngredientName, tfIngredientQuantity, tfIngredientUnit);
        clearLabels(lblRecipeNameMsg, lblIngredientMsg, lblCategoryMsg, lblCategory, lblSuccess);
        tfMethod.setText("");
        
        // display successfull message
        lblSuccess.setText("تم إضافة الطبخة بنجاح"); 
    }

    @FXML
    // Action in add Button to add ingredients in list not database   
    private void addIngredient(ActionEvent event) {
        
        // get the infromation of each ingredient
        String name = tfIngredientName.getText();
        String quantity = tfIngredientQuantity.getText();
        String unit = tfIngredientUnit.getText();

        // validate ingredients fields
        lblIngredientMsg.setTextFill(Color.rgb(188, 5, 5));
        if (name.isEmpty() | quantity.isEmpty() | unit.isEmpty()) {
            lblIngredientMsg.setText("يجب عليك إكمال جميع الحقول الخاصة بالمكون");
        }
        else if (!quantity.matches("[0-9]+(\\.[0-9]+)?")) {
            lblIngredientMsg.setText("يجب أن يكون المقدار رقما");
        }        
        else if (ingredients.contains(new Ingredient(name))){ 
            lblIngredientMsg.setText("هذا المكون تم إضافته مسبقا");
        }
        else {
            // add the ingredient to temprerly array
            ingredients.add(new Ingredient(name, new Double(quantity), unit));
            lblIngredientMsg.setTextFill(Color.GREEN);
            lblIngredientMsg.setText("تم إضافة " + name + " بنجاح "); 
            clearTextFields(tfIngredientName, tfIngredientQuantity, tfIngredientUnit);
        }
    }
    
    @FXML
    // These methods to choose category of recipe
    private void chooseDrinks(ActionEvent event) {
        recipeToAdd.setCategory(Category.DRINKS);
        lblCategory.setText(Category.DRINKS);
    }

    @FXML
    private void chooseAppetizer(ActionEvent event) {
        recipeToAdd.setCategory(Category.APPETIZER);
        lblCategory.setText(Category.APPETIZER);
    }

    @FXML
    private void chooseDessert(ActionEvent event) {
        recipeToAdd.setCategory(Category.DESSERT);
        lblCategory.setText(Category.DESSERT);
    }

    @FXML
    private void chooseMainCourse(ActionEvent event) {
        recipeToAdd.setCategory(Category.MAIN_COURSE);
        lblCategory.setText(Category.MAIN_COURSE);
    }
    
    // clear all text fields will sent to it.
    private void clearTextFields(TextField... tfs) {
        for (TextField tf: tfs) {
            tf.setText("");
        }
    }
    
    // clear all Labels will sent to it.
    private void clearLabels(Label... lbls) {
        for (Label lbl: lbls) {
            lbl.setText("");
        }
    }

    // when user put the focus (Pointer) in some field 
    private void clearLabels(MouseEvent event) {
        clearLabels(lblRecipeNameMsg, lblIngredientMsg, lblCategoryMsg, lblSuccess);
    }

    @FXML
    private void goSettingKitchen(ActionEvent event) throws IOException {
        Navigation.changeScene(event, "/view/KitchenSetting.fxml");
    }
    
    @FXML
    private void back(ActionEvent event) throws IOException {
        Navigation.changeScene(event, "/view/MyRecipes.fxml");
    }   
}
