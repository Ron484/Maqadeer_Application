package database;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="foodstuff")
public class FoodStuff implements Serializable{
    
    @Id
    @Column(name="food_id")
    private int id;
    
    @Column(name="food_name")
    private String name;
    
    @Column(name="quantity")
    private double quantity;
    
    @Column(name="quantity_unit")
    private String quantityUnit;
  
    @Column(name="expire_date")
    private String expireDate;
    
    @Column(name="food_section")
    private String foodSection;           
    
    @Column(name="user_id")
    private int userId;
 

    public FoodStuff() {
    }

    public FoodStuff(int id, String name, double quantity, String quantityUnit, String expireDate, String section, int userId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.expireDate = expireDate;
        this.foodSection = section;
        this.userId = userId;
    }

    public FoodStuff(String name, double quantity, String quantityUnit, String expireDate, String section, int userId) {
        this.name = name;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.expireDate = expireDate;
        this.foodSection = section;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getFoodSection() {
        return foodSection;
    }

    public void setFoodSection(String foodSection) {
        this.foodSection = foodSection;
    }   

    @Override
    public String toString() {
        return "FoodStuff{" + "id=" + id + ", name=" + name + ", quantity=" + quantity + ", quantityUnit=" + quantityUnit + ", expireDate=" + expireDate + ", mySection=" + foodSection + ", userId=" + userId + '}';
    }   
}
