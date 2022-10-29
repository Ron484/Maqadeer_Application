/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static database.HibernateUtil.getSessionFactory;
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
import java.util.Date;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author huawei
 */
public class ShoppingListController extends Navigation{

    @FXML
    private TextField tf1;

    @FXML
    private ComboBox cb;

    //for combobox
    ObservableList<String> data = FXCollections.observableArrayList();

// date
    Date date = new Date();

    //for listView
    ObservableList<String> adding = FXCollections.observableArrayList();

    @FXML
    private ListView l1 = new ListView(adding);

    private Label lblErrorMsg;
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

    //adding an item to buy
    private void Add(ActionEvent event) {
        Session session = null;

        String add = tf1.getText();
        if (add.isEmpty()) {
            lblErrorMsg.setText("هذا الحقل فارغ");
            return;
        }
        if (adding.contains(add)) {
            lblErrorMsg.setText("The item already inserted");
            return;
        }

        session = getSessionFactory().openSession();
        session.beginTransaction();

        adding.add(add);

    }

    //adding items to combobox
    @FXML
    private void ch(ActionEvent event) {
        Session session = null;

    }
    //checking expiry date

//    private ShoppingListController(ActionEvent event) {
//        Session session = null;
//        try {
//            Object ed
//                    = session.createQuery("FROM foodstuff WHERE expire_date >= " + date)
//                            .uniqueResult();
//
//            if (ed == null) {
//
//                lblErrorMsg.setText("لقد انتهت صلاحيته ");
//
//            }
//            else {
//                lblErrorMsg.setText("لم تنتهي صلاحيته ");
//            }
//            session.getTransaction().commit();
//            session.close();
//
//        }
//        catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        finally {
//            session.getTransaction().commit();
//            session.close();
//            System.out.println("Closed session");
//        }
//
//    }   
}
