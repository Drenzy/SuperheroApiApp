package com.example.superheroapp;

import com.example.superheroapp.Superhero;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

// Interface defining API endpoints using Retrofit annotations
public interface ApiService {

    // Endpoint to retrieve a list of all superheroes
    @GET("/api/superheroes")
    Call<List<Superhero>> getAllSuperheros();

    // Endpoint to retrieve a list of all superhero brands
    @GET("/api/superheroes/brands")
    Call<List<String>> getAllBrands();

    // Endpoint to upload a new superhero
    @POST("/api/superheroes")
    Call<Void> uploadSuperhero(@Body Superhero superhero);

    // Endpoint to update an existing superhero by ID
    @PUT("/api/superheroes/{id}")
    Call<Void> updateSuperhero(@Path("id") int superheroId, @Body Superhero superhero);

    // Endpoint to delete a superhero by ID
    @DELETE("/api/superheroes/{id}")
    Call<Void> deleteSuperhero(@Path("id") int superheroId);
}
