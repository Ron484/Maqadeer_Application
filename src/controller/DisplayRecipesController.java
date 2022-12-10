package controller;

import static database.HibernateUtil.getSessionFactory;
import database.NewRecipe;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Rehab
 */
public class DisplayRecipesController {

    @FXML
    private AnchorPane panRoot;
    @FXML
    private Label lbTitel;
    @FXML
    private TextField txlink;
    @FXML
    private TextField txdishname;
    @FXML
    private Button btadd;

    @FXML
    private ScrollPane viewWindow;

    @FXML
    private Label lbMsgError;

    private List<NewRecipe> recipe;

    Hyperlink hyper = new Hyperlink();

   // FlowPane pane = new FlowPane();
    
    GridPane pane=new GridPane();
    int col=0,row=0;

    String[] images = new String[]{
        "view/images/cupcake.png",
        "view/images/pancake.png",
        "view/images/cake.png",
        "view/images/lollipop.png"};

    String[] style = new String[]{
        "-fx-border-color:#FF0099;-fx-border-width:2;-fx-border-radius:5;-fx-background-color:#FFD5D5;-fx-pref-width:100;-fx-pref-height:20",
        "-fx-border-color:#3FA3AB;-fx-border-width:2;-fx-border-radius:5;-fx-background-color:#BDD3D5;-fx-pref-width:100;-fx-pref-height:20",
        "-fx-border-color:#FCB55A;-fx-border-width:2;-fx-border-radius:5;-fx-background-color:#FDE4C3;-fx-pref-width:100;-fx-pref-height:20",
        "-fx-border-color:#669FF5;-fx-border-width:2;-fx-border-radius:5;-fx-background-color:#CADFFE;-fx-pref-width:100;-fx-pref-height:20"
    };
    @FXML
    private Button btAdd1;
    @FXML
    private Label lblNoRecipe;

    public void setHyper(Hyperlink hyper, String image, String recipe, String link, String style) {
        ImageView img = new ImageView(image);
        img.setFitWidth(40);
        img.setFitHeight(40);
      
        hyper.setStyle(style);
        hyper.setOpaqueInsets(new Insets(20, 20, 20, 20));
        hyper.setText(recipe);
        hyper.setFont(Font.font(15));
        hyper.setAlignment(Pos.CENTER);
        hyper.setContentDisplay(ContentDisplay.TOP);
        hyper.setGraphic(img);
        hyper.setOnAction(e -> {

            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (URISyntaxException ex) {
                Logger.getLogger(DisplayRecipesController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DisplayRecipesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        
        pane.setPrefWidth(336);
        pane.setPrefHeight(373);
        pane.setHgap(10);
        pane.setVgap(10);
        //pane.getChildren().add(hyper);
        pane.setStyle("-fx-background-color:white");
       
        pane.add(hyper, row, col);
        row++;
        if(row==3){
            row=0;
            col++;
        }
        
        viewWindow.setPadding(new Insets(5, 0, 0, 10));
        viewWindow.setContent(pane);

    }

    public void view(List list, String section) {

        if (!list.isEmpty()) {
            recipe = list;
            lbTitel.setText(section);
            int j = 0;
            for (int i = 0; i < recipe.size(); i++) {
                if (images.length == j) {
                    j = 0;
                }
                setHyper(new Hyperlink(), images[j], recipe.get(i).getName(), recipe.get(i).getLink(), style[j]);
                j++;
            }

        } else {
            recipe = list;
            lbTitel.setText(section);

        }

    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Navigation.changeScene(event, "/view/NewRecipes.fxml");
    }

    private boolean chkURL(String url) {

        String regex = "((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(url);

        return m.matches();

    }

    @FXML
    private void add(ActionEvent event) {

        NewRecipe rec = new NewRecipe();
        rec.setName(txdishname.getText());
        rec.setLink(txlink.getText());
        rec.setSecion(lbTitel.getText());
        rec.setUserId(CurrentUser.id);

        recipe.removeAll(recipe);
        recipe.add(rec);

        if (txdishname.getText().isEmpty() || txlink.getText().isEmpty()) {
            lbMsgError.setText("يرجى تعبئة جميع الحقول");            
        } else if (!chkURL(txlink.getText())) {
            lbMsgError.setText("الرابط غير صحيح");           
        } else {
            lbMsgError.setText("");
            System.out.println("صحيح");
            Session session = getSessionFactory().openSession();
            session.beginTransaction();

            session.save(rec);
            System.out.println("done");
            session.getTransaction().commit();
            session.close();

            view(recipe, lbTitel.getText());
        }
    }

}
