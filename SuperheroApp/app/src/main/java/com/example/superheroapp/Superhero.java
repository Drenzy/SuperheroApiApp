package com.example.superheroapp;

import com.google.gson.annotations.SerializedName;

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
