package com.example.superhero.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table
public class Superhero {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    @Column(nullable = false)
    private String Name;
    @Column(nullable = false)
    private String Secret_identity;
    @Column(nullable = false)
    private int Age;
    @Column(nullable = false)
    private Brand brand;

    public enum Brand {
        Marvel, DC, Image_Comics, Dark_Horse;
    }

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
