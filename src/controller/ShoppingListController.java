package controller;

import database.DB;
import database.FoodStuff;
import static database.HibernateUtil.getSessionFactory;
import database.ShoppingList;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.hibernate.Query;

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
    private Label lblEmptyPane2;    

    private String lableStyle = "-fx-background-radius: 15; -fx-alignment: center; -fx-padding: 2;"
            + " -fx-background-color: #F9D98B; -fx-pref-width: 300; -fx-pref-height: 27;";

    private final File SHOPPING_LIST_FILE = new File("shoppingList.txt");

    // to store them in file later
//    ArrayList<String> itemsWillExpire = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        display();
        displayWillExipre();
    }

    // that user add maneually    
    private void display() {

        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM ShoppingList WHERE user_id = ?");
        query.setParameter(0, CurrentUser.id);

        List<ShoppingList> all = query.list();

        session.getTransaction().commit();
        session.close();

        pane1.getChildren().clear();
        for (ShoppingList item : all) {            
            pane1.getChildren().add(createLabel(item.getName()));
        }
    }

    public void displayWillExipre() {

        List<FoodStuff> list = DB.getList("FROM FoodStuff WHERE userId = ?", CurrentUser.id);

        if (!list.isEmpty()) {
            pane11.getChildren().remove(lblEmptyPane2);
        }

        // get all items that will end recently in 2 days or less
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
            + " -fx-background-color: #F9D98B; -fx-pref-width: 300; -fx-pref-height: 27;");
        label.setOnMouseClicked(e-> {
            tfSearch.setText(lblText);
        });
        return label;
    } 

    @FXML
    private void remove(ActionEvent event) {

        String remove = tfSearch.getText();

        if (remove.isEmpty()) {
            lblErrorMsg.setText("هذا الحقل فارغ");
            return;
        }

        ShoppingList obj = DB.getObject("FROM ShoppingList WHERE userId = ? AND name= ? ", CurrentUser.id, remove);
        if (obj == null) {
            lblErrorMsg.setText("هذا المكون غير موجود ");
        }

        else {
            DB.delete(obj);
            display();
            lblErrorMsg.setText("تم حذف المكون بنجاح");
        }
    }

    @FXML
    private void add(ActionEvent event) throws FileNotFoundException {

        String add1 = tfSearch.getText();

        lblErrorMsg.setTextFill(Color.rgb(188, 5, 5));

        if (add1.isEmpty()) {
            lblErrorMsg.setText("هذا الحقل فارغ");
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

        List<ShoppingList> all = DB.getList("FROM ShoppingList WHERE user_id = ?", CurrentUser.id);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(SHOPPING_LIST_FILE);
            writer.println("--------- قائمة الشراء ---------");
            for (ShoppingList item : all) {
                writer.println(item.getName());
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            writer.close();
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
