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

public interface ApiService {
    @GET("/api/superheroes")
    Call<List<Superhero>> getAllSuperheros();

    @POST("/api/superheroes")
    Call<Void> uploadSuperhero(@Body Superhero superhero);

    @PUT("/api/superheroes/{id}")
    Call<Void> updateSuperhero(@Path("id") int superheroId, @Body Superhero superhero);

    @DELETE("/api/superheroes/{id}")
    Call<Void> deleteSuperhero(@Path("id") int superheroId);
}
