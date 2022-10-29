/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static database.HibernateUtil.getSessionFactory;
import database.NewRecipe;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author user
 */
public class DisplayRecipesController  {

    @FXML
    private AnchorPane panRoot;
    @FXML
    private Label lbTitel;
    @FXML
    private TextField txlink;
    @FXML
    private TextField txdishname;
    @FXML
    private Circle btadd;
    @FXML
    private ScrollPane viewWindow;
    
    private List <NewRecipe> recipe  ;

   
    
   
    Hyperlink hyper = new Hyperlink();
  
    
  //  GridPane pane = new GridPane();
  FlowPane pane = new FlowPane();

    ImageView[] images = new  ImageView[]{
    new ImageView("view/images/cupcake.png"),
    new ImageView("view/images/pancake.png"),
    new ImageView("view/images/cake.png"),
    new ImageView("view/images/lollipop.png")};
    
    String [] style = new String []{
     "-fx-border-color:#FF9A50;-fx-border-width:2;-fx-border-radius:5;-fx-background-color:#FFD5D5",
     "-fx-border-color:#FF9A50;-fx-border-width:2;-fx-border-radius:5;-fx-background-color:#BDD3D5",
     "-fx-border-color:#FF9A50;-fx-border-width:2;-fx-border-radius:5;-fx-background-color:#FDE4C3",
     "-fx-border-color:#FF9A50;-fx-border-width:2;-fx-border-radius:5;-fx-background-color:#CADFFE"
    };
    
    
    public void setHyper ( Hyperlink hyper, ImageView image ,String recipe,String link ,String style ){
        image.setFitWidth(40);
        image.setFitHeight(40);
        hyper.setStyle(style);
        hyper.setOpaqueInsets(new Insets(20,20,20,20));
        hyper.setText(recipe);
        hyper.setFont(Font.font(15));
        hyper.setContentDisplay(ContentDisplay.TOP);
        hyper.setGraphic(image);
        hyper.setOnAction(e->{
            
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (URISyntaxException ex) {
                Logger.getLogger(DisplayRecipesController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DisplayRecipesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        pane.setHgap(10);
        pane.setVgap(10);
        pane.getChildren().add(hyper);
      
        viewWindow.setPadding(new Insets(5,0,0,10));
        viewWindow.setContent(pane);
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public List<NewRecipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<NewRecipe> recipe) {
        this.recipe = recipe;
    }

    
   public void view (List list , String section ) {
      
       if(!list.isEmpty()){
       recipe=list;
       lbTitel.setText(section);
       int j=0;
       for(int i=0;i<recipe.size();i++){
          if(images.length==j){
             j=0;
          }
          setHyper(new Hyperlink(),images[j],recipe.get(i).getName(),recipe.get(i).getLink(),style[j]); 
          j++;
       }
       
       }
       else{
         recipe=list;
         lbTitel.setText(section);
       
       }    
       
        
      
       
       
   } 

   

    @FXML
    private void add(MouseEvent event) {
        NewRecipe rec = new NewRecipe();
        rec.setName(txdishname.getText());
        rec.setLink(txlink.getText());
        rec.setSecion(lbTitel.getText());
        rec.setUserId(2);
        
        recipe.removeAll(recipe);
        recipe.add(rec);
        
         Session session = getSessionFactory().openSession();
         session.beginTransaction();
          
        
         
         session.save(rec);
         System.out.println("done");
         session.getTransaction().commit();
         session.close();
         
         view(recipe,lbTitel.getText());

        
    }

    
}
