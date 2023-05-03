package org.H4212.entities;


import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Person {

    @Id
    @GeneratedValue
    @NotNull
    private Long id;
    @Column(name = "LastName")
    @NotNull
    private String lastName;
    @Column(name = "FirstName")
    @NotNull
    private String firstName;

    @Column(name = "Username")
    @NotNull
    private String username;

    @Column(name = "User password")
    @NotNull
    private String password;

    public Person(String lastName, String firstName, String username, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.password = password;
    }

    public Person() {

    }

    public Long getId() {
        return id;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JsonObject toJson(){
        return Json.createBuilderFactory(null).createObjectBuilder()
                .add("lastName", lastName)
                .add("firstName", firstName).build();
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
