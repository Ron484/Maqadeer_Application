package controller;

import database.DB;
import database.FoodStuff;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Ekram
 */
public class KitchenSettingController implements Initializable {
    
    /*
        In this class, There are 3 panes for each operation: add, update, delete
        that each contains the needed feilds for it. when click the button will 
        delete the other panes and left the needed one.        
    */

    @FXML
    private AnchorPane rootPane;
    
    // adding pane feilds
    @FXML
    private VBox addingPane;
    @FXML
    private ComboBox<String> cboItemSection;
    @FXML
    private DatePicker tfExpireDateAdd;
    @FXML
    private TextField tfNameAdd;
    @FXML
    private TextField tfQAdd;
    @FXML
    private TextField tfUnitAdd;
    @FXML
    private Label lblAddMsg;

    // deleting pane feilds
    @FXML
    private VBox deletingPane;
    @FXML
    private TextField tfNameDelete;
    @FXML
    private Label lblDeleteMsg;

    // updaing pane feilds
    @FXML
    private VBox udpatePane;
    @FXML
    private ComboBox<String> cboUdateSection;
    @FXML
    private DatePicker tfExpireDateUpdate;
    @FXML
    private TextField tfUdpdateName;
    @FXML
    private TextField tfQUpdate;
    @FXML
    private TextField tfUnitUpdate;
    @FXML
    private Label lblUpdateMsg;

    @FXML
    private Label lblConfirm;

    private final String FRIDGE = "ثلاجة";
    private final String FREEZER = "فريزر";
    private final String SPICES = "بهارات";
    private final String OTHER = "أخرى";

    // depending on opration show the feilds 
    private boolean update = true;
    private boolean add = false;
    private boolean delete = false;

    FoodStuff objToUpdate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // set the items to combobox
        cboUdateSection.getItems().addAll(FRIDGE, FREEZER, SPICES, OTHER);
        cboItemSection.getItems().addAll(FRIDGE, FREEZER, SPICES, OTHER);

        // for update: make it easy found the item to update it
        tfUdpdateName.textProperty().addListener(lstnr -> {
            
            String name = tfUdpdateName.getText();
            objToUpdate = DB.getObject("FROM FoodStuff WHERE userId = ? AND name = ?",
                    CurrentUser.id, name);

            if (objToUpdate != null) {
           
                // if element found, complete its information in other feild to help user know it                
                tfQUpdate.setPromptText(String.valueOf(objToUpdate.getQuantity()));
                tfUnitUpdate.setPromptText(objToUpdate.getQuantityUnit());
                tfExpireDateUpdate.setValue(LocalDate.parse(objToUpdate.getExpireDate()));
                cboUdateSection.setValue(objToUpdate.getFoodSection());
                clearLabels(lblUpdateMsg);
                update = true;
            }
        });

    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Navigation.changeScene(event, "/view/MyKitchen.fxml");
    }

    @FXML
    private void addFood(ActionEvent event) {
        // display only the adding pane
        rootPane.getChildren().removeAll(udpatePane, addingPane, deletingPane);
        rootPane.getChildren().add(addingPane);
        
        // used by confirm button to know which operaion feilds will check
        add = true;
        delete = update = false;
    }

    private void add() {
        // get the values from feilds 
        String name = tfNameAdd.getText();
        String quan = tfQAdd.getText();
        String unit = tfUnitAdd.getText();
        String date = String.valueOf(tfExpireDateAdd.getValue());
        String section = cboItemSection.getValue();

        lblAddMsg.setTextFill(Color.rgb(188, 5, 5)); // RED

        // if user complete the fieldes
        if (name.isEmpty() | quan.isEmpty() | unit.isEmpty() | date.isEmpty()) {
            lblAddMsg.setText("يجب عليك إكمال جميع الحقول");
        }

        // check duplicated items
        else if (DB.isExist("FROM FoodStuff WHERE userId = ? AND name = ?",
                CurrentUser.id, name)) {
            lblAddMsg.setText("العنصر موجود مسبقا");
        }

        // if user choose the section from combobox
        else if (section == null) {
            lblAddMsg.setText("اختر مكان العنصر");
        }

        // if user enters non-numerical values in quantity feild
        else if (!quan.matches("[0-9]+(\\.[0-9]+)?")) {
            lblAddMsg.setText("يجب أن يكون المقدار رقما");
        }
        else {
            // complete all checking validation successfull, so now add
            DB.save(new FoodStuff(name, new Double(quan), unit, date, section, CurrentUser.id));
            lblAddMsg.setTextFill(Color.GREEN);
            lblAddMsg.setText("تم إضافة " + name + " بنجاح");
            
            // clear the feilds for another adding
            clearTextFields(tfNameAdd, tfQAdd, tfUnitAdd);
            cboItemSection.setValue("اختر مكان العنصر");
            tfExpireDateAdd.setValue(null);
        }
    }

    @FXML
    private void updateFood(ActionEvent event) {
        
        // only display update pane
        rootPane.getChildren().removeAll(udpatePane, addingPane, deletingPane);
        rootPane.getChildren().add(udpatePane);
        
        // used by confirm button to know which operaion feilds will check
        update = true;
        delete = add = false;
    }

    private void update() {
        
        // get the values from feilds
        String name = tfUdpdateName.getText();
        String quan = tfQUpdate.getText();
        String unit = tfUnitUpdate.getText();
        String date = String.valueOf(tfExpireDateUpdate.getValue());
        String section = cboUdateSection.getValue();

        lblUpdateMsg.setTextFill(Color.rgb(188, 5, 5)); // RED
        
        // check if user complete the feilds in right way
        if (quan.isEmpty() | unit.isEmpty()) {
            lblUpdateMsg.setText("أكمل جميع الحقول");
            return;
        }
        // check if exist: the value is assigned in listern
        if (objToUpdate == null) {            
            lblUpdateMsg.setText("لا يوجد عنصر بهذا الاسم");
            return;
        }
        
        // check if quantity is numerical value
        else if (!quan.matches("[0-9]+(\\.[0-9]+)?")) {
            lblUpdateMsg.setText("يجب أن يكون المقدار رقما");
            return;
        }        
            
        // check if user update
        boolean makeUpdate = false;
        if (!objToUpdate.getName().equals(name)) { // item`s name
            objToUpdate.setName(name);
            makeUpdate = true;
        }
        if (!objToUpdate.getExpireDate().equals(date)) { // item`s expire date
            objToUpdate.setExpireDate(date);
            makeUpdate = true;
        }
        if (!objToUpdate.getFoodSection().equals(section)) { // item`s section
            objToUpdate.setFoodSection(section);
            makeUpdate = true;
        }
        if (objToUpdate.getQuantity() != new Integer(quan)) { // item`s quantitiy
            objToUpdate.setQuantity(new Integer(quan));
            makeUpdate = true;
        }
        if (!objToUpdate.getQuantityUnit().equals(unit)) { // item`s quantitiy unit
            objToUpdate.setQuantityUnit(unit);
            makeUpdate = true;
        }

        // if user has cahnge any feild of the item then update
        if (makeUpdate) {
            DB.update(objToUpdate);
            lblUpdateMsg.setTextFill(Color.GREEN);
            lblUpdateMsg.setText("تم تعديل " + name + " بنجاح");
            
            clearTextFields(tfUdpdateName, tfQUpdate, tfUnitUpdate);
            cboUdateSection.setValue("اختر مكان العنصر");
            tfQUpdate.setPromptText("المقدار");
            tfUnitUpdate.setPromptText("الوحدة");
            tfExpireDateUpdate.setValue(null);
        }

        // the user doesn`t change any thing
        else { 
            lblUpdateMsg.setText("لم يتم تغيير شئ في العنصر");
        }
    }

    @FXML
    private void deleteFood(ActionEvent event) {
        
        // only display deleting pane
        rootPane.getChildren().removeAll(udpatePane, addingPane, deletingPane);
        rootPane.getChildren().add(deletingPane);
        
        // used by confirm button to know which operaion feilds will check
        delete = true;
        add = update = false;
    }

    private void delete() {

        String name = tfNameDelete.getText();

        // the user has entered item`s name
        if (name.isEmpty()) {
            lblDeleteMsg.setTextFill(Color.rgb(188, 5, 5)); // RED
            lblDeleteMsg.setText("أدخل اسم العنصر لحذفه");
            return;
        }

        // get the object to delete
        FoodStuff obj = DB.getObject("FROM FoodStuff WHERE userId = ? AND name = ?",
                CurrentUser.id, name);

        // if item is null, then it`s not exist
        if (obj == null) {
            lblDeleteMsg.setTextFill(Color.rgb(188, 5, 5)); // RED
            lblDeleteMsg.setText("لا يوجد عنصر بهذا الاسم");
        }
        else {
            // the item is exist, then delete it
            DB.delete(obj);
            lblDeleteMsg.setTextFill(Color.GREEN);
            lblDeleteMsg.setText(" تم حذف " + name + " بنجاح");            
            clearTextFields(tfNameDelete);           
        }
    }

    // depending on selected operaion, call method that check its fields
    @FXML
    private void confirm(ActionEvent event) {

        if (update) {
            update();
        }

        else if (delete) {
            delete();
        }

        else if (add) {
            add();
        }
    }

    // clear all text fields will sent to it.
    private void clearTextFields(TextField... tfs) {
        for (TextField tf : tfs) {
            tf.setText("");
        }
    }

    // clear all Labels will sent to it.
    private void clearLabels(Label... lbls) {
        for (Label lbl : lbls) {
            lbl.setText("");
        }
    }
}
