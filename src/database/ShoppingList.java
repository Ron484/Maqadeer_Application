package database;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="shopping_list")
public class ShoppingList implements Serializable {
    
    @Id
    @Column(name="shopping_list_id")
    private int id;
    
    @Column(name="user_id")
    private int userId;

    public ShoppingList() {
    }

    public ShoppingList(int userId) {
        this.userId = userId;
    }

    public ShoppingList(int id, int userId) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ShoppingList{" + "id=" + id + ", userId=" + userId + '}';
    }
}
