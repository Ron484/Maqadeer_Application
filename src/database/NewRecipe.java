package database;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="new_recipe")
public class NewRecipe implements Serializable {
    
    @Id
    @Column(name="new_recipe_id")       
    private int id;
    
    @Column(name="new_recipe_name")       
    private String name;
    
    @Column(name="section")       
    private String secion;
    
    @Column(name="link")       
    private String link;
    
    @Column(name="user_id")       
    private int userId;

    public NewRecipe() {
    }

    public NewRecipe(String name, String secion, String link, int userId) {
        this.name = name;
        this.secion = secion;
        this.link = link;
        this.userId = userId;
    }

    public NewRecipe(int id, String name, String secion, String link, int userId) {
        this.id = id;
        this.name = name;
        this.secion = secion;
        this.link = link;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecion() {
        return secion;
    }

    public void setSecion(String secion) {
        this.secion = secion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }   

    @Override
    public String toString() {
        return "NewRecipe{" + "id=" + id + ", name=" + name + ", secion=" + secion + ", link=" + link + ", userId=" + userId + '}';
    }
}
