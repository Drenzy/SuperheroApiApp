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

    public String setName(String name) {
        return name;
    }

    public String setBrand(String brand) {
        return brand;
    }

    public int setAge(int age) {
        return age;
    }

    public String setSecretIdentity(String secretIdentity) {
        return secretIdentity;
    }
}
