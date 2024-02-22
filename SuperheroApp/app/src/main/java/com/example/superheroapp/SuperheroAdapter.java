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

    private void showEditDialog(Superhero superhero) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Superhero");

        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_edit_superhero, null);
        builder.setView(viewInflated);

        EditText editName = viewInflated.findViewById(R.id.editTextEditName);
        EditText editBrand = viewInflated.findViewById(R.id.editTextEditBrand);
        EditText editAge = viewInflated.findViewById(R.id.editTextEditAge);
        EditText editSecretIdentity = viewInflated.findViewById(R.id.editTextEditSecretIdentity);

        // Set initial values in the dialog
        editName.setText(superhero.getName());
        editBrand.setText(superhero.getBrand());
        editAge.setText(String.valueOf(superhero.getAge()));
        editSecretIdentity.setText(superhero.getSecretIdentity());

        builder.setPositiveButton("Save Changes", (dialogInterface, i) -> {
            String editedName = editName.getText().toString();
            String editedBrand = editBrand.getText().toString();
            int editedAge = Integer.parseInt(editAge.getText().toString());
            String editedSecretIdentity = editSecretIdentity.getText().toString();

            superhero.setName(editedName);
            superhero.setBrand(editedBrand);
            superhero.setAge(editedAge);
            superhero.setSecretIdentity(editedSecretIdentity);

            updateSuperhero(superhero);
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        builder.show();
    }

    private void updateSuperhero(Superhero superhero) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<Void> updateCall = apiService.updateSuperhero(superhero.getId(), superhero);

        updateCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("API Response", "Superhero details updated successfully");
                    notifyDataSetChanged();
                } else {
                    Log.e("API Response", "Error: " + response.code() + ", " + response.message());

                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("API Response", "Error Body: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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

        Call<Void> deleteCall = apiService.deleteSuperhero(superheroId);

        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("API Response", "Superhero deleted successfully");
                    // Remove the deleted superhero from the list
                    removeSuperheroFromList(superheroId);
                    notifyDataSetChanged();
                } else {
                    Log.e("API Response", "Error: " + response.code() + ", " + response.message());

                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("API Response", "Error Body: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    showToast("Error deleting superhero. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API Response", "Failed to make API call", t);
                showToast("Failed to delete superhero. Please check your internet connection and try again.");
            }
        });
    }

    // Helper method to remove a superhero from the list in the adapter
    private void removeSuperheroFromList(int superheroId) {
        for (int i = 0; i < getCount(); i++) {
            Superhero superhero = getItem(i);
            if (superhero != null && superhero.getId() == superheroId) {
                remove(superhero);
                break;
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
