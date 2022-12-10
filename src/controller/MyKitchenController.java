package controller;

import database.DB;
import database.FoodStuff;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Ekram
 */
public class MyKitchenController extends Navigation implements Initializable {

    @FXML
    private TextField tfSearch;

    @FXML
    private VBox foodPane;

    @FXML
    private ToggleGroup sections;
    @FXML
    private ToggleButton btOther;
    @FXML
    private ToggleButton btSpices;
    @FXML
    private ToggleButton btFreezer;
    @FXML
    private ToggleButton btFridge;
    @FXML
    private ToggleButton btAll;

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblEmpty;

    private final String ALL = "all";
    private final String FRIDGE = "ثلاجة";
    private final String FREEZER = "فريزر";
    private final String SPICES = "بهارات";
    private final String OTHER = "أخرى";

    private String selectedSection = ALL;
    private ToggleButton previousSelected;

    private String[] colors = {"#ADDAD1", " #FEA597", "#CCDACD", "#F9D98B"};
    private int i = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // display all food stuff when navigate to this scene
        List<FoodStuff> list = getFoodStuff();
        displayFoodStuff(list);
        
        // by default the selected section is ALL
        previousSelected = btAll;

        // using listener to search immediatly when writing in search field
        tfSearch.textProperty().addListener(listener -> {            
            List<FoodStuff> result = search(tfSearch.getText());            
            displayFoodStuff(result);
        });
    }

    // RETRIEVE food stuff from database
    public List<FoodStuff> getFoodStuff() {

        return selectedSection.equals(ALL)
                ? DB.getList("FROM FoodStuff WHERE userId = ?", CurrentUser.id)
                : DB.getList("FROM FoodStuff WHERE userId = ? AND foodSection = ?",
                        CurrentUser.id, selectedSection);
    }

    // DISPLALY the foodstuff in the interface GUI
    public void displayFoodStuff(List<FoodStuff> foodStuff) {
        
        // delete previous displayed foodstuff
        foodPane.getChildren().clear();
        
        // there is a empty label behind the content bring to front
        if (foodStuff.isEmpty()) {            
            root.getChildren().remove(lblEmpty);
            root.getChildren().add(lblEmpty);
            return;
        }
        
        // bring en empty label to backward
        root.getChildren().remove(lblEmpty);
        root.getChildren().add(0, lblEmpty);

        // display the items in VBox
        for (FoodStuff item : foodStuff) {

            // create VBox has item info: name, quantity, expire date    
            Label lblName = new Label(item.getName());
            lblName.setStyle("-fx-alignment: center; -fx-pref-width: 100");
            
            Label lblQuantity = new Label(item.getQuantity() + " " + item.getQuantityUnit());
            lblQuantity.setStyle("-fx-alignment: center; -fx-pref-width: 50");
            
            Label lblExpireDate = new Label(item.getExpireDate());
            lblQuantity.setStyle("-fx-alignment: center; -fx-pref-width: 100");
            
            HBox itemHbox = new HBox(10, lblName, lblQuantity, lblExpireDate);
            itemHbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            itemHbox.setStyle("-fx-background-radius: 15; -fx-alignment: center; "
                            + "-fx-padding: 20; -fx-background-color: " + colors[i]);    
            i = (i + 1) % colors.length;
            foodPane.getChildren().add(itemHbox);
        }
    }

    // SEARCH for foodstuff in specific section
    public List<FoodStuff> search(String food) {   
        // search in all foodstuff sections or in specific section
        return selectedSection.equals(ALL) 
            ? DB.getList("FROM FoodStuff WHERE userId = ? AND name LIKE ?",
              CurrentUser.id, "%" + food + "%")
                
            : DB.getList("FROM FoodStuff WHERE userId = ? AND foodSection = ? AND name LIKE ?",
              CurrentUser.id, selectedSection, "%" + food + "%");
    }

    @FXML
    // it will assing the section to selected one
    private void chooseSection(ActionEvent event) {
        
        // which button has selected
        ToggleButton selectedButton = (ToggleButton) sections.getSelectedToggle();
        if (selectedButton == null) { // becuase it`s toggle button 
            return;
        }
        
        // change button color to black and reset the previous selected
        selectedButton.setStyle("-fx-background-color:#464141; -fx-text-fill: white;-fx-background-radius: 12;");
        previousSelected.setStyle("-fx-background-color:white; -fx-text-fill: black;-fx-background-radius: 12;");
        previousSelected = selectedButton;

        // assign the section depending on selected button
        if (selectedButton.equals(btAll)) 
            selectedSection = ALL;
        
        else if (selectedButton.equals(btFridge)) 
            selectedSection = FRIDGE;

        else if (selectedButton.equals(btFreezer))
            selectedSection = FREEZER;
        
        else if (selectedButton.equals(btSpices)) 
            selectedSection = SPICES;

        else if (selectedButton.equals(btOther)) 
            selectedSection = OTHER;        
        
        // when click check if there is sth in search field
        String searchFor = tfSearch.getText();        
        List<FoodStuff> list = searchFor.isEmpty() ? getFoodStuff() : search(searchFor);
        displayFoodStuff(list); 
    }

    @FXML
    private void goKitchenSetting(ActionEvent event) throws IOException {
        Navigation.changeScene(event, "/view/KitchenSetting.fxml");
    }    
}

