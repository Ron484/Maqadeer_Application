package controller;

import static database.HibernateUtil.getSessionFactory;
import database.Recipe;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Rehab
 */
public class MyRecipesController extends Navigation {

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

    @FXML
    private Label lblMsg;

    private ToggleButton previousSelected;
    private ToggleButton currentSelected;

    @FXML
    private void goToAddingPage(ActionEvent event) throws IOException {
        Navigation.changeScene(event, "/view/AddingRecipe.fxml");
    }

    private void searchResult(String result) {

        if (lbresult1.getText().isEmpty()) {
            lbresult1.setText(result);
        }
        else if (lbresult2.getText().isEmpty()) {
            lbresult2.setText(result);
        }
        else if (lbresult3.getText().isEmpty()) {
            lbresult3.setText(result);
        }
        else if (lbresult4.getText().isEmpty()) {
            lbresult4.setText(result);
        }
        else {
            lbresult1.setText(result);
            lbresult2.setText("");
            lbresult3.setText("");
            lbresult4.setText("");
        }
    }

    private void checkSearchResult(List<Recipe> recipe) {
        if (recipe.isEmpty()) {

            lblMsg.setText("نتائج البحث غير متوفره");
            panRoot.getChildren().remove(lblMsg);
            panRoot.getChildren().add(lblMsg);

        }
        else {
            lblMsg.setText("");
            panRoot.getChildren().remove(lblMsg);
            panRoot.getChildren().add(0, lblMsg);

            searchResult(recipe.get(0).getName());

        }

    }

    private Scene move(Recipe recipe) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ViewRecipe.fxml"));
        Parent viewRecipe = loader.load();
        Scene scene = new Scene(viewRecipe);
        scene.getStylesheets().add(Navigation.class.getResource("/view/style.css").toExternalForm());

        ViewRecipeController controller = loader.getController();

        if (recipe != null) {

            controller.view(recipe.getMethod(), String.valueOf(recipe.getId()), recipe.getName(), false);

        }

        return scene;

    }

    @FXML
    private void searchAction(ActionEvent event) {

        Session session = getSessionFactory().openSession();
        if (!btMain.isSelected() && !btAppetizer.isSelected() && !btDrink.isSelected() && !btdessert.isSelected()) {
       
            btAll.setSelected(true);
            session.beginTransaction();

            Query query = session.createSQLQuery("SELECT * from Recipe where recipe_name=? and user_id=?").addEntity(Recipe.class);
            query.setString(0, tfSearch.getText());
            query.setString(1, String.valueOf(CurrentUser.id));

            List<Recipe> recipe = query.list();

            checkSearchResult(recipe);

            session.getTransaction().commit();

        }
        else if (btMain.isSelected()) {

            session.beginTransaction();

            Query query = session.createSQLQuery("SELECT * from Recipe where recipe_name=? and category=? and user_id=?").addEntity(Recipe.class);
            query.setString(0, tfSearch.getText());
            query.setString(1, btMain.getText());
            query.setString(2, String.valueOf(CurrentUser.id));

            List<Recipe> recipe = query.list();

            checkSearchResult(recipe);

            session.getTransaction().commit();

        }
        else if (btAppetizer.isSelected()) {

            session.beginTransaction();

            Query query = session.createSQLQuery("SELECT * from Recipe where recipe_name=? and category=? and user_id=?").addEntity(Recipe.class);
            query.setString(0, tfSearch.getText());
            query.setString(1, btAppetizer.getText());
            query.setString(2, String.valueOf(CurrentUser.id));

            List<Recipe> recipe = query.list();
            checkSearchResult(recipe);

            session.getTransaction().commit();

        }
        else if (btDrink.isSelected()) {

            session.beginTransaction();

            Query query = session.createSQLQuery("SELECT * from Recipe where recipe_name=? and category=? and user_id=?").addEntity(Recipe.class);
            query.setString(0, tfSearch.getText());
            query.setString(1, btDrink.getText());
            query.setString(2, String.valueOf(CurrentUser.id));

            List<Recipe> recipe = query.list();
            checkSearchResult(recipe);

            session.getTransaction().commit();

        }
        else if (btdessert.isSelected()) {

            session.beginTransaction();

            Query query = session.createSQLQuery("SELECT * from Recipe where recipe_name=? and category=? and user_id = ?").addEntity(Recipe.class);
            query.setString(0, tfSearch.getText());
            query.setString(1, btdessert.getText());
            query.setString(2, String.valueOf(CurrentUser.id));

            List<Recipe> recipe = query.list();
            checkSearchResult(recipe);

            session.getTransaction().commit();

        }

        session.close();

    }

    @FXML
    private void ViewAction1(MouseEvent event) throws IOException {

        Session session = getSessionFactory().openSession();

        session.beginTransaction();

        Query query = session.createSQLQuery("SELECT * from Recipe where recipe_name=? and user_id=?").addEntity(Recipe.class);
        query.setString(0, lbresult1.getText());
        query.setString(1, String.valueOf(CurrentUser.id));

        Recipe recipe = (Recipe) query.uniqueResult();

        session.getTransaction().commit();

        session.close();

        Scene scene = move(recipe);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

        stage.show();

    }

    @FXML
    private void ViewAction2(MouseEvent event) throws IOException {

        Session session = getSessionFactory().openSession();

        session.beginTransaction();

        Query query = session.createSQLQuery("SELECT * from Recipe where recipe_name=? and user_id=?").addEntity(Recipe.class);
        query.setString(0, lbresult2.getText());
        query.setString(1, String.valueOf(CurrentUser.id));

        Recipe recipe = (Recipe) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        Scene scene = move(recipe);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

        stage.show();

    }

    @FXML
    private void ViewAction3(MouseEvent event) throws IOException {

        Session session = getSessionFactory().openSession();

        session.beginTransaction();

        Query query = session.createSQLQuery("SELECT * from Recipe where recipe_name=? and user_id=?").addEntity(Recipe.class);
        query.setString(0, lbresult3.getText());
        query.setString(1, String.valueOf(CurrentUser.id));

        Recipe recipe = (Recipe) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        Scene scene = move(recipe);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void ViewAction4(MouseEvent event) throws IOException {

        Session session = getSessionFactory().openSession();

        session.beginTransaction();

        Query query = session.createSQLQuery("SELECT * from Recipe where recipe_name=? and user_id=?").addEntity(Recipe.class);
        query.setString(0, lbresult4.getText());
        query.setString(1, String.valueOf(CurrentUser.id));

        Recipe recipe = (Recipe) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        Scene scene = move(recipe);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    // these method to change the button of selected one
    @FXML
    private void chooseDessert(ActionEvent event) {
        changeColorButton(btdessert);

    }

    @FXML
    private void chooseDrinks(ActionEvent event) {
        changeColorButton(btDrink);

    }

    @FXML
    private void chooseAppetizer(ActionEvent event) {
        changeColorButton(btAppetizer);

    }

    @FXML
    private void chooseMain(ActionEvent event) {
        changeColorButton(btMain);
    }

    @FXML
    private void chooseAll(ActionEvent event) {
        changeColorButton(btAll);
    }

    private void changeColorButton(ToggleButton btn) {
        if (previousSelected == null) previousSelected = btAll;
        previousSelected.setStyle("-fx-background-color:white; -fx-text-fill: black;-fx-background-radius: 12;");
        currentSelected = btn;
        currentSelected.setStyle("-fx-background-color:#464141; -fx-text-fill: white;-fx-background-radius: 12;");
        previousSelected = currentSelected;
    }

}
