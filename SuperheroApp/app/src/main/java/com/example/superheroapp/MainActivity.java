package com.example.superheroapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Superhero> superheroes;
    private SuperheroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        superheroes = new ArrayList<>();
        adapter = new SuperheroAdapter(this, R.layout.list_item_superhero, superheroes);
        listView.setAdapter(adapter);


        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Making the API request to get all superheroes
        Call<List<Superhero>> call = apiService.getAllSuperheros();

        call.enqueue(new Callback<List<Superhero>>() {
            @Override
            public void onResponse(Call<List<Superhero>> call, Response<List<Superhero>> response) {
                if (response.isSuccessful()) {
                    superheroes.addAll(response.body());
                    adapter.notifyDataSetChanged();
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
