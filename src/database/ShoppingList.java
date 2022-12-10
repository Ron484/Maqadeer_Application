package database;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "shopping_list")
public class ShoppingList implements Serializable {

    @Id
    @Column(name = "item_name")
    private String name;   
   
    @Id
    @Column(name = "user_id")
    private int userId;

    public ShoppingList() {
    }

    public ShoppingList(String name, int userId) {
        this.name = name;
        this.userId = userId;
    }    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ShoppingListItem{" + "id=" + name + ", shoppingListId=" + userId + '}';
    }
}
