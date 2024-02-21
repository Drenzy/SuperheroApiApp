package com.example.superheroapp;

import com.example.superheroapp.Superhero;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ApiService {
    @GET("/api/superheros") // Replace with your actual endpoint
    Call<List<Superhero>> getAllSuperheros();
}
