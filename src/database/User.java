package database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
    
    @Id
    @Column(name="user_id")
    private int id;
    
    @Column(name="user_name")
    private String name;
    
    @Column(name="user_email")
    private String Email;
    
    @Column(name="user_password")
    private String password;

    public User() {
    }

    public User(String name, String Email, String password) {
        this.name = name;
        this.Email = Email;
        this.password = password;
    }   

    public User(int id, String name, String Email, String password) {
        this.id = id;
        this.name = name;
        this.Email = Email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", Email=" + Email + ", password=" + password + '}';
    }
}
