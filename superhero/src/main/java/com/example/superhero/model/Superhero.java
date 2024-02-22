package com.example.superhero.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table
public class Superhero {

    // Primary key for the Superhero entity, automatically generated using IDENTITY strategy
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    // Superhero's name, mapped to a non-nullable column in the database
    @Column(nullable = false)
    private String Name;

    // Superhero's secret identity, mapped to a non-nullable column in the database
    @Column(nullable = false)
    private String Secret_identity;

    // Superhero's age, mapped to a non-nullable column in the database
    @Column(nullable = false)
    private int Age;

    // Superhero's brand, an enum representing different comic book publishers
    @Column(nullable = false)
    private Brand brand;

    // Enum representing different superhero brands or comic book publishers
    public enum Brand {
        Marvel, DC, Image_Comics, Dark_Horse;
    }

    // Getter and setter methods for the Superhero class properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSecret_identity() {
        return Secret_identity;
    }

    public void setSecret_identity(String secret_identity) {
        Secret_identity = secret_identity;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        this.Age = age;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

}
