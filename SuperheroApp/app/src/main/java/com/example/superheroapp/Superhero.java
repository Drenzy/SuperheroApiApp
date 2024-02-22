package com.example.superheroapp;

import com.google.gson.annotations.SerializedName;

// Model class representing a Superhero with fields annotated for Gson serialization
public class Superhero {
    @SerializedName("id")
    private int id;

    @SerializedName("brand")
    private String brand;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private int age;

    @SerializedName("secret_identity")
    private String secretIdentity;


    // Getter methods to retrieve values of private fields
    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSecretIdentity() {
        return secretIdentity;
    }

    // Setter methods to update values of private fields
    public void setName(String name) {
        this.name=name;
    }

    public void setBrand(String brand) {
        this.brand=brand;
    }

    public void setAge(int age) {
        this.age=age;
    }

    public void setSecretIdentity(String secretIdentity) {
        this.secretIdentity=secretIdentity;
    }
}
