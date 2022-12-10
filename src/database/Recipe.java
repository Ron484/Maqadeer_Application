package database;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recipe")
public class Recipe implements Serializable {

    @Id
    @Column(name = "recipe_id")
    private int id;

    @Column(name = "recipe_name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "method")
    private String method;

    @Column(name = "user_id")
    private int userId;

    public Recipe() {
    }

    public Recipe(int userId) {
        this.userId = userId;
    }

    public Recipe(int id, String name, String category, String method, int userId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.method = method;
        this.userId = userId;
    }

    public Recipe(String name, String category, String method, int userId) {
        this.name = name;
        this.category = category;
        this.method = method;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Recipe{" + "id=" + id + ", name=" + name + ", category=" + category + ", method=" + method + ", userId=" + userId + '}';
    }
}
