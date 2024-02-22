package com.example.superheroapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<Superhero> superheroes;
    private SuperheroAdapter adapter;
    private Spinner spinnerBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing UI components
        ListView listView = findViewById(R.id.listView);
        superheroes = new ArrayList<>();

        // Creating an adapter for the list view to display superheroes
        adapter = new SuperheroAdapter(this, R.layout.list_item_superhero, superheroes);
        listView.setAdapter(adapter);

        // Making the API request to get all superheroes
        fetchSuperheroes();

        /* Handle button click to add a new superhero */
        Button buttonAddSuperhero = findViewById(R.id.buttonAddSuperhero);
        buttonAddSuperhero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Method to handle the addition of a new superhero
                addNewSuperhero();
            }
        });

        // Initializing the spinner for selecting superhero brands
        spinnerBrand = findViewById(R.id.spinnerBrand);
    }

    private void fetchSuperheroes() {
        // Create an instance of ApiService using the ApiClient
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Make an asynchronous API request to get all superheroes
        Call<List<Superhero>> call = apiService.getAllSuperheros();

        call.enqueue(new Callback<List<Superhero>>() {
            @Override
            public void onResponse(Call<List<Superhero>> call, Response<List<Superhero>> response) {
                // Check if the API response is successful (HTTP status code in the 2xx range)
                if (response.isSuccessful()) {
                    superheroes.clear(); // Clear the existing list
                    superheroes.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    // Populate spinner with brands
                    populateSpinnerWithBrands();
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


    private void populateSpinnerWithBrands() {
        // Create an instance of ApiService using the ApiClient
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Make an asynchronous API request to get all superhero brands
        Call<List<String>> call = apiService.getAllBrands();

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                // Check if the API response is successful (HTTP status code in the 2xx range)
                if (response.isSuccessful()) {
                    // Get the list of superhero brands from the API response
                    List<String> brands = response.body();

                    // Create an ArrayAdapter for the spinner, using the list of brands
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, brands);

                    // Specify the layout for the dropdown items
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Set the adapter for the spinner, populating it with the superhero brands
                    spinnerBrand.setAdapter(spinnerAdapter);
                } else {
                    // Handle error response
                    Log.e("API Response", "Error: " + response.code() + ", " + response.message());

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                // Handle failure
                Log.e("API Response", "Failed to make API call", t);

            }
        });
    }





    private void addNewSuperhero() {
        // Retrieve references to the UI input fields
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextAge = findViewById(R.id.editTextAge);
        EditText editTextSecretIdentity = findViewById(R.id.editTextSecretIdentity);

        // Extract user input from the UI fields
        String name = editTextName.getText().toString();
        String brand = spinnerBrand.getSelectedItem().toString(); // Get selected brand from spinner
        int age = Integer.parseInt(editTextAge.getText().toString());
        String secretIdentity = editTextSecretIdentity.getText().toString();

        // Create a new Superhero object with the user-provided information
        Superhero newSuperhero = new Superhero();
        newSuperhero.setName(name);
        newSuperhero.setBrand(brand);
        newSuperhero.setAge(age);
        newSuperhero.setSecretIdentity(secretIdentity);

        // Upload the new superhero to the server
        uploadNewSuperhero(newSuperhero);
    }

    private void uploadNewSuperhero(Superhero newSuperhero) {
        // Create an instance of ApiService using the ApiClient
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Make an asynchronous API request to upload the new superhero
        Call<Void> uploadCall = apiService.uploadSuperhero(newSuperhero);

        uploadCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Check if the API response is successful (HTTP status code in the 2xx range)
                if (response.isSuccessful()) {
                    // Handle successful upload
                    Log.d("API Response", "Superhero uploaded successfully");

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
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure to make the API call
                Log.e("API Response", "Failed to make API call", t);

            }
        });


    }
}