package controller;

import database.DB;
import database.FoodStuff;
import database.ShoppingList;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


/**
 *
 * @author Nouf
 */
public class ShoppingListController extends Navigation implements Initializable {

    @FXML
    private Label lblErrorMsg;

    @FXML
    private TextField tfSearch;
    
    @FXML
    private VBox pane1;
    
    @FXML
    private VBox pane11;
  
    
    //the file that will items write to it
    private final File SHOPPING_LIST_FILE = new File("shoppingList.txt");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        
        // get the items from database and display
        display(); 
        displayWillExipre(); // all items that finiseded 
    }

    // items user add maneually    
    private void display()  {        
        
        // bring items from db
        List<ShoppingList> all = DB.getList("FROM ShoppingList WHERE user_id = ?", CurrentUser.id);
        
        // remove all exist items
        pane1.getChildren().clear();
        
        // display itemsby creating label for each then add it to pane
        for (ShoppingList item : all) {            
            pane1.getChildren().add(createLabel(item.getName()));
        }
    }

    public void displayWillExipre() {

        // bring items from db
        List<FoodStuff> list = DB.getList("FROM FoodStuff WHERE userId = ?", CurrentUser.id);

        // get all items that end 
        for (FoodStuff item : list) {
            LocalDate start = LocalDate.parse(item.getExpireDate());
            LocalDate end = LocalDate.now(); // date of today
            long diffrence = DAYS.between(start, end);
            if (diffrence >= 0) {               
                pane11.getChildren().add(createLabel(item.getName()));
            }
        }
    }
    
    private Label createLabel(String lblText) {
        Label label = new Label(lblText);
        label.setStyle("-fx-background-radius: 15; -fx-alignment: center; -fx-padding: 2;"
            + " -fx-background-color: #F9D98B; -fx-pref-width: 300; -fx-pref-height: 30;");
        
        // when click on item, it will appear in textfield to deleted easly
        label.setOnMouseClicked(e-> {
            tfSearch.setText(lblText);
        });
        label.setCursor(Cursor.HAND);
        return label;
    } 

    @FXML
    // if user want to delete items from shopping list
    private void remove(ActionEvent event) {

        String remove = tfSearch.getText();

        if (remove.isEmpty()) {
            lblErrorMsg.setTextFill(Color.rgb(188, 5, 5));
            lblErrorMsg.setText("يجب عليك كتابة اسم المكون");
            return;
        }

        ShoppingList obj = DB.getObject("FROM ShoppingList WHERE userId = ? AND name= ? ", 
                CurrentUser.id, remove);
        
        if (obj == null) {
            lblErrorMsg.setTextFill(Color.rgb(188, 5, 5));
            lblErrorMsg.setText("هذا المكون غير موجود ");
        }

        else {
            DB.delete(obj);
            display(); // redisplay the items
            lblErrorMsg.setTextFill(Color.GREEN);
            lblErrorMsg.setText("تم حذف المكون بنجاح");
        }
    }

    @FXML
    private void add(ActionEvent event) throws FileNotFoundException {

        String add1 = tfSearch.getText();

        lblErrorMsg.setTextFill(Color.rgb(188, 5, 5));

        if (add1.isEmpty()) {
            lblErrorMsg.setText("يجب عليك كتابة اسم المكون");
        }

        else if (DB.isExist("FROM ShoppingList WHERE userId = ? AND name= ? ", CurrentUser.id, add1)) {
            lblErrorMsg.setText("هذا المكون موجود مسبقا");
        }

        else {
            DB.save(new ShoppingList(add1, CurrentUser.id));
            lblErrorMsg.setTextFill(Color.GREEN);
            lblErrorMsg.setText("تمت اضافة المكون بنجاح");
            pane1.getChildren().add(createLabel(add1));
        }
    }

    // write the item that user add in shopping list
    private void writeToFile() {

        List<ShoppingList> all = DB.getList("FROM ShoppingList WHERE user_id = ?", 
                CurrentUser.id);       
        
        try(PrintWriter writer = new PrintWriter(SHOPPING_LIST_FILE)) {            
            writer.println("--------- قائمة الشراء ---------");
            for (ShoppingList item : all) 
                writer.println(item.getName());            
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void openFile() {

        try {

            //check if Desktop is supported by Platform or not  
            if (!Desktop.isDesktopSupported()) {
                System.out.println("not supported");
            }
            else {
                //opens the specified file  
                Desktop desktop = Desktop.getDesktop();
                desktop.open(SHOPPING_LIST_FILE);
            }
        }
        catch (IOException e) {
            System.out.println("تعذر فتح الملف");
        }
    }

    @FXML
    // when click on export button 
    // write the item in file
    // then open it
    private void exportToFile(ActionEvent event) {
        writeToFile();
        openFile();
    }
}
