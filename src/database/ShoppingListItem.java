package database;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Embeddable


@Entity
@Table(name = "shopping_list_item")
public class ShoppingListItem implements Serializable {

    @Id
    @Column(name = "item_id")
    private int id;

    @Column(name = "section")
    private String secion;
   
    @Column(name = "shopping_list_id")
    private int shoppingListId;

    public ShoppingListItem() {
    }

    public ShoppingListItem(String secion, int shoppingListId) {
        this.secion = secion;
        this.shoppingListId = shoppingListId;
    }

    public ShoppingListItem(int id, String secion, int shoppingListId) {
        this.id = id;
        this.secion = secion;
        this.shoppingListId = shoppingListId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecion() {
        return secion;
    }

    public void setSecion(String secion) {
        this.secion = secion;
    }

    public int getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(int shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    
    // hash code and equals are important because of composit PK 
    // I haven`t understand it 
//    @Override
//    public int hashCode() {
//       
//        int result = 1;
//        result += id;
//        result += shoppingListId;
//        return result;
//        
////        final int prime = 31;
////        int result = 1;
////        result = prime * result + acc_id;
////        result = prime * result + user_id;
////        return result;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//
//        ShoppingListItem item = (ShoppingListItem) obj;
//        if (this.id != item.id) {
//            return false;
//        }
//
//        if (this.shoppingListId != item.shoppingListId) {
//            return false;
//        }
//
//        return true;
//    }

    @Override
    public String toString() {
        return "ShoppingListItem{" + "id=" + id + ", secion=" + secion + ", shoppingListId=" + shoppingListId + '}';
    }
}
