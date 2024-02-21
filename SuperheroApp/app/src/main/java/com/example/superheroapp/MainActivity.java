package com.example.superheroapp;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assuming you have an ApiService instance
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Making the API request to get all superheroes
        Call<List<Superhero>> call = apiService.getAllSuperheros();

        call.enqueue(new Callback<List<Superhero>>() {
            @Override
            public void onResponse(Call<List<Superhero>> call, Response<List<Superhero>> response) {
                if (response.isSuccessful()) {
                    List<Superhero> superheroes = response.body();
                    // Process the list of superheroes, e.g., update UI
                    for (Superhero superhero : superheroes) {
                        Log.d("Superhero", superhero.getName());
                        Log.d("Superhero", superhero.getSecretIdentity());
                        Log.d("Superhero", superhero.getBrand());
                        Log.d("Superhero", new String(String.valueOf(superhero.getAge())));
                    }
                } else {
                    // Handle error response
                    Log.e("API Response", "Error: " + response.code() + ", " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Superhero>> call, Throwable t) {
                // Handle failure
                Log.e("API Response", "Failed to make API call", t);
            }
        });
    }
}
