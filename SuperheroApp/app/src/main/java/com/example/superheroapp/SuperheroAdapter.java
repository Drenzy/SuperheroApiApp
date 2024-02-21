package com.example.superheroapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

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

            nameTextView.setText(superhero.getName());
            secretIdentityTextView.setText("Secret Identity: " + superhero.getSecretIdentity());
            brandTextView.setText("Brand: " + superhero.getBrand());
            ageTextView.setText("Age: " + superhero.getAge());
        }

        return convertView;
    }
}
