package database;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ingredient")
public class Ingredient implements Serializable {
    
    @Id
    @Column(name="ingredient_id")
    private int id;    
   
    @Column(name="ingredient_name")
    private String name;
    
    @Column(name="quantity")
    private double quantity;
    
    @Column(name="quantity_unit")
    private String quantityUnit;
    
    @Column(name="recipe_id")
    private int recipeId; 

    public Ingredient() {
    }
    
    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(String name, double quantity, String quantityUnit, int recipeId) {
        this.name = name;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.recipeId = recipeId;
    }

    public Ingredient(int id, String name, double quantity, String quantityUnit, int recipeId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.recipeId = recipeId;
    }
    
    public Ingredient(String name, double quantity, String quantityUnit) {        
        this.name = name;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;       
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
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

    @Override
    public String toString() {
        return "Ingredient{" + "id=" + id + ", name=" + name + ", quantity=" 
            + quantity + ", quantityUnit=" + quantityUnit + ", recipeId=" + recipeId + '}';
    }   

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ingredient other = (Ingredient) obj;
        if (!Objects.equals(this.name, other.name)) {        
            return false;
        }
        return true;
    }
}
