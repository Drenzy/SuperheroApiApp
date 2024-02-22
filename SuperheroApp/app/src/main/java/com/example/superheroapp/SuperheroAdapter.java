package com.example.superheroapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.superheroapp.ApiClient;
import com.example.superheroapp.ApiService;
import com.example.superheroapp.R;
import com.example.superheroapp.Superhero;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuperheroAdapter extends ArrayAdapter<Superhero> {

    private Context context;
    private int resource;

    public SuperheroAdapter(Context context, int resource, List<Superhero> superheroes) {
        super(context, resource, superheroes);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
        }

        Superhero superhero = getItem(position);

        if (superhero != null) {
            TextView nameTextView = convertView.findViewById(R.id.superheroNameTextView);
            TextView secretIdentityTextView = convertView.findViewById(R.id.superheroSecretIdentityTextView);
            TextView brandTextView = convertView.findViewById(R.id.superheroBrandTextView);
            TextView ageTextView = convertView.findViewById(R.id.superheroAgeTextView);

            Button deleteButton = convertView.findViewById(R.id.buttonDelete);

            nameTextView.setText(superhero.getName());
            secretIdentityTextView.setText("Secret Identity: " + superhero.getSecretIdentity());
            brandTextView.setText("Brand: " + superhero.getBrand());
            ageTextView.setText("Age: " + superhero.getAge());

            // Set up the delete button click listener
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle delete button click
                    deleteSuperhero(superhero.getId());
                }
            });
        }

        return convertView;
    }

    private void deleteSuperhero(int superheroId) {
        // Implement the API call to delete the superhero with the given ID
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<Void> deleteCall = apiService.deleteSuperhero(superheroId);

        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful deletion
                    removeSuperhero(superheroId); // Remove the superhero from the adapter
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
                    showToast("Error deleting superhero. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Log.e("API Response", "Failed to make API call", t);

                // Display a message to the user indicating the error
                showToast("Failed to delete superhero. Please check your internet connection and try again.");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // Helper method to remove a superhero from the adapter by ID
    private void removeSuperhero(int superheroId) {
        for (int i = 0; i < getCount(); i++) {
            Superhero superhero = getItem(i);
            if (superhero != null && superhero.getId() == superheroId) {
                remove(superhero);
                notifyDataSetChanged();
                break;
            }
        }
    }
}
