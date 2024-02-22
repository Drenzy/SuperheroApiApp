package com.example.superheroapp;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Custom ArrayAdapter for displaying Superhero items in a ListView
public class SuperheroAdapter extends ArrayAdapter<Superhero> {
    private Context context;
    private int resource;


    // Constructor to initialize the adapter with a context, resource ID, and list of superheroes
    public SuperheroAdapter(Context context, int resource, List<Superhero> superheroes) {
        super(context, resource, superheroes);
        this.context = context;
        this.resource = resource;
    }

    // Override getView to customize the appearance of each item in the ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate the layout for each list item if it's not already inflated
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
        }

        // Get the Superhero object at the specified position
        Superhero superhero = getItem(position);

        // Update the UI components with Superhero information
        if (superhero != null) {
            TextView nameTextView = convertView.findViewById(R.id.superheroNameTextView);
            TextView secretIdentityTextView = convertView.findViewById(R.id.superheroSecretIdentityTextView);
            TextView brandTextView = convertView.findViewById(R.id.superheroBrandTextView);
            TextView ageTextView = convertView.findViewById(R.id.superheroAgeTextView);

            Button editButton = convertView.findViewById(R.id.buttonEdit);
            Button deleteButton = convertView.findViewById(R.id.buttonDelete);

            nameTextView.setText(superhero.getName());
            secretIdentityTextView.setText("Secret Identity: " + superhero.getSecretIdentity());
            brandTextView.setText("Brand: " + superhero.getBrand());
            ageTextView.setText("Age: " + superhero.getAge());

            // Set up the edit button click listener
            editButton.setOnClickListener(view -> showEditDialog(superhero));

            // Set up the delete button click listener
            deleteButton.setOnClickListener(view -> removeSuperhero(superhero.getId()));
        }

        return convertView;
    }

    // Display a dialog to edit superhero details
    private void showEditDialog(Superhero superhero) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Superhero");

        // Inflate the layout for the edit dialog
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_edit_superhero, null);
        builder.setView(viewInflated);

        // Reference UI elements in the edit dialog
        EditText editName = viewInflated.findViewById(R.id.editTextEditName);
        Spinner spinnerBrand = viewInflated.findViewById(R.id.spinnerBrand); // Change to spinner
        EditText editAge = viewInflated.findViewById(R.id.editTextEditAge);
        EditText editSecretIdentity = viewInflated.findViewById(R.id.editTextEditSecretIdentity);

        // Set initial values in the dialog
        editName.setText(superhero.getName());
        editAge.setText(String.valueOf(superhero.getAge()));
        editSecretIdentity.setText(superhero.getSecretIdentity());

        // Populate the spinner with brands
        populateSpinnerWithBrands(spinnerBrand, superhero.getBrand());

        builder.setPositiveButton("Save Changes", (dialogInterface, i) -> {
            // Get updated values from the dialog
            String editedName = editName.getText().toString();
            String editedBrand = spinnerBrand.getSelectedItem().toString(); // Get selected brand from spinner
            int editedAge = Integer.parseInt(editAge.getText().toString());
            String editedSecretIdentity = editSecretIdentity.getText().toString();

            // Update superhero object with edited values
            superhero.setName(editedName);
            superhero.setBrand(editedBrand);
            superhero.setAge(editedAge);
            superhero.setSecretIdentity(editedSecretIdentity);

            // Call API to update superhero details
            updateSuperhero(superhero);
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        // Display the edit dialog
        builder.show();
    }

    // Update superhero details via API
    private void updateSuperhero(Superhero superhero) {
        // Make API request to update superhero details
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<Void> updateCall = apiService.updateSuperhero(superhero.getId(), superhero);

        updateCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Check if the API response is successful
                if (response.isSuccessful()) {
                    Log.d("API Response", "Superhero details updated successfully");
                    // Notify the adapter that data has changed
                    notifyDataSetChanged();
                } else {
                    // Handle error response
                    Log.e("API Response", "Error: " + response.code() + ", " + response.message());

                    try {
                        // Log additional details from the response body
                        String errorBody = response.errorBody().string();
                        Log.e("API Response", "Error Body: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Show a toast message indicating an error
                    showToast("Error updating superhero details. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API Response", "Failed to make API call", t);
                showToast("Failed to update superhero details. Please check your internet connection and try again.");
            }
        });
    }

    // Helper method to remove a superhero from the adapter by ID
    private void removeSuperhero(int superheroId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Make API request to delete a superhero
        Call<Void> deleteCall = apiService.deleteSuperhero(superheroId);

        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Check if the API response is successful
                if (response.isSuccessful()) {
                    Log.d("API Response", "Superhero deleted successfully");
                    // Remove the deleted superhero from the list
                    removeSuperheroFromList(superheroId);
                    // Notify the adapter that data has changed
                    notifyDataSetChanged();
                } else {
                    // Handle error response
                    Log.e("API Response", "Error: " + response.code() + ", " + response.message());

                    try {
                        // Log additional details from the response body
                        String errorBody = response.errorBody().string();
                        Log.e("API Response", "Error Body: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Show a toast message indicating an error
                    showToast("Error deleting superhero. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Log.e("API Response", "Failed to make API call", t);
                showToast("Failed to delete superhero. Please check your internet connection and try again.");
            }
        });
    }

    // Helper method to remove a superhero from the list in the adapter
    private void removeSuperheroFromList(int superheroId) {
        // Iterate through the list of superheroes in the adapter
        for (int i = 0; i < getCount(); i++) {
            Superhero superhero = getItem(i);
            // Check if the superhero exists and has the specified ID
            if (superhero != null && superhero.getId() == superheroId) {
                // Remove the superhero from the list
                remove(superhero);
                break; // Break the loop since the superhero has been found and removed
            }
        }
    }

    // Display a short toast message
    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // Populate the spinner with superhero brands
    private void populateSpinnerWithBrands(Spinner spinner, String selectedBrand) {
        // Create an instance of the API service
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Make API request to get all superhero brands
        Call<List<String>> call = apiService.getAllBrands();

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                // Check if the API response is successful
                if (response.isSuccessful()) {
                    // Get the list of superhero brands from the response body
                    List<String> brands = response.body();
                    // Create an ArrayAdapter for the spinner
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, brands);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Set the adapter for the spinner
                    spinner.setAdapter(spinnerAdapter);

                    // Select the previously selected brand
                    if (selectedBrand != null) {
                        // Find the position of the selected brand in the list
                        int position = brands.indexOf(selectedBrand);
                        // Set the spinner selection to the position of the selected brand
                        if (position != -1) {
                            spinner.setSelection(position);
                        }
                    }
                } else {
                    // Handle error response
                    Log.e("API Response", "Error: " + response.code() + ", " + response.message());
                    //...
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                // Handle failure
                Log.e("API Response", "Failed to make API call", t);
                //...
            }
        });
    }
}
