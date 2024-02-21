package com.example.superheroapp;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
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

        // Making the API request to get all superheroes
        fetchSuperheroes();

        /* Handle button click to add a new superhero */
        Button buttonAddSuperhero = findViewById(R.id.buttonAddSuperhero);
        buttonAddSuperhero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewSuperhero();
            }
        });
    }

    private void fetchSuperheroes() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<List<Superhero>> call = apiService.getAllSuperheros();

        call.enqueue(new Callback<List<Superhero>>() {
            @Override
            public void onResponse(Call<List<Superhero>> call, Response<List<Superhero>> response) {
                if (response.isSuccessful()) {
                    superheroes.clear(); // Clear the existing list
                    superheroes.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle error response
                    Log.e("API Response", "Error: " + response.code() + ", " + response.message());

                    // Log additional details from the response body
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("API Response", "Error Body: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Display a message to the user indicating the error
                    showToast("Error fetching superheroes. Please try again.");

                    // You may also consider adding a retry mechanism or other error handling strategies here
                }
            }

            @Override
            public void onFailure(Call<List<Superhero>> call, Throwable t) {
                // Handle failure
                Log.e("API Response", "Failed to make API call", t);

                // Display a message to the user indicating the error
                showToast("Failed to fetch superheroes. Please check your internet connection and try again.");

                // You may also consider adding a retry mechanism or other error handling strategies here
            }
        });
    }

    private void addNewSuperhero() {
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextBrand = findViewById(R.id.editTextBrand);
        EditText editTextAge = findViewById(R.id.editTextAge);
        EditText editTextSecretIdentity = findViewById(R.id.editTextSecretIdentity);

        // Retrieve data from EditText fields
        String name = editTextName.getText().toString();
        String brand = editTextBrand.getText().toString();
        int age = Integer.parseInt(editTextAge.getText().toString());
        String secretIdentity = editTextSecretIdentity.getText().toString();

        // Create a new superhero object
        Superhero newSuperhero = new Superhero();
        newSuperhero.setName(name);
        newSuperhero.setBrand(brand);
        newSuperhero.setAge(age);
        newSuperhero.setSecretIdentity(secretIdentity);

        // Upload the new superhero
        uploadNewSuperhero(newSuperhero);
    }

    private void uploadNewSuperhero(Superhero newSuperhero) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Making the API request to upload a new superhero
        Call<Void> uploadCall = apiService.uploadSuperhero(newSuperhero);

        uploadCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful upload
                    Log.d("API Response", "Superhero uploaded successfully");

                    // Clear input fields
                    clearInputFields();

                    // Refresh the superhero list
                    fetchSuperheroes();
                } else {
                    // Handle error response
                    Log.e("API Response", "Error: " + response.code() + ", " + response.message());

                    // Log additional details from the response body
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("API Response", "Error Body: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Display a message to the user indicating the error
                    showToast("Error adding superhero. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Log.e("API Response", "Failed to make API call", t);

                // Display a message to the user indicating the error
                showToast("Failed to add superhero. Please check your internet connection and try again.");
            }
        });
    }

    private void clearInputFields() {
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextBrand = findViewById(R.id.editTextBrand);
        EditText editTextAge = findViewById(R.id.editTextAge);
        EditText editTextSecretIdentity = findViewById(R.id.editTextSecretIdentity);

        editTextName.setText("");
        editTextBrand.setText("");
        editTextAge.setText("");
        editTextSecretIdentity.setText("");
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}