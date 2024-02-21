package com.example.superheroapp;

import com.example.superheroapp.Superhero;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ApiService {
    @GET("/api/superheros")
    Call<List<Superhero>> getAllSuperheros();

    @POST("/api/superheros")
    Call<Void> uploadSuperhero(@Body Superhero superhero);
}
