/* This DB is for Maqadeer App */
CREATE SCHEMA maqadeer;
USE maqadeer;

-- It`s for sign-up, log-in that will make user accesses to all apps` services 
CREATE TABLE user (
	user_id INT NOT NULL AUTO_INCREMENT,
	user_name VARCHAR(30) NOT NULL UNIQUE,
    user_email VARCHAR(40) NOT NULL UNIQUE,
    user_password VARCHAR(20) NOT NULL,
    CONSTRAINT USER_PK PRIMARY KEY (user_id)
);

-- It`s for keeping all recipes that user knows to cook.
CREATE TABLE recipe (
	recipe_id INT NOT NULL AUTO_INCREMENT,
	recipe_name VARCHAR(30) NOT NULL UNIQUE,    
    category VARCHAR(30) NOT NULL,
    method VARCHAR(255),   
    user_id INT NOT NULL,
    CONSTRAINT RECIPE_PK PRIMARY KEY (recipe_id),
    CONSTRAINT RECIPE_FK FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- It`s the ingredients for all recipes
CREATE TABLE ingredient (
	ingredient_id INT NOT NULL AUTO_INCREMENT,
	ingredient_name VARCHAR(30) NOT NULL UNIQUE,   
    quantity DOUBLE NOT NULL,
    quantity_unit VARCHAR(20) NOT NULL,    
    recipe_id INT NOT NULL,
    CONSTRAINT INGREDIENT PRIMARY KEY (ingredient_id),
    CONSTRAINT INGREDIENT_FK FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id) ON DELETE CASCADE
);

-- It keeps the links of new recipes that user wish to learn
CREATE TABLE new_recipe ( 
	new_recipe_id INT NOT NULL AUTO_INCREMENT,
	new_recipe_name VARCHAR(30),    
    section VARCHAR(30) NOT NULL,
    link VARCHAR(255),   
    user_id INT NOT NULL,
    CONSTRAINT NEWRECIPE_PK PRIMARY KEY (new_recipe_id),
    CONSTRAINT NEWRECIPE_FK FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- It`s what inside the user`s kitchen to suggest the recipe from it
CREATE TABLE foodstuff (
	food_id INT NOT NULL AUTO_INCREMENT,
	food_name VARCHAR(30) NOT NULL UNIQUE,
    quantity DOUBLE DEFAULT 0,
    quantity_unit VARCHAR(20) NOT NULL,
    expire_date DATE,
    section VARCHAR(30) NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT FOODSTUFF_PK PRIMARY KEY (food_id),
    CONSTRAINT FOODSTUFF_FK FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- It`s shopping list 
CREATE TABLE shopping_list (
	 shopping_list_id INT NOT NULL AUTO_INCREMENT,     
     user_id INT NOT NULL,
     CONSTRAINT SHOPPINGLIST_PK PRIMARY KEY (shopping_list_id),
	 CONSTRAINT SHOPPINGLIST_FK FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- It`s for items in shopping list that added: manually by user, or automaticly when expried.
CREATE TABLE shopping_list_item (
	 item_id INT NOT NULL AUTO_INCREMENT,  
     section VARCHAR(30) NOT NULL CHECK(section IN ("manually", "expired")),    
     shopping_list_id INT NOT NULL,     
     CONSTRAINT ITEM_PK PRIMARY KEY (item_id, shopping_list_id),
	 CONSTRAINT ITEM_FK FOREIGN KEY (shopping_list_id) REFERENCES shopping_list(shopping_list_id) ON DELETE CASCADE
);
