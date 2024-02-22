package com.example.superheroapp;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Class responsible for creating and providing a Retrofit instance for API communication
public class ApiClient {

    // Retrofit instance for handling API requests
    private static Retrofit retrofit = null;

    // Method to get or create a Retrofit instance with a specified base URL
    public static Retrofit getClient() {
        // If the Retrofit instance does not exist, create a new one
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    // Set the base URL for the API
                    .baseUrl(ApiConstants.BASE_URL)
                    // Add Gson converter factory for handling JSON responses
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        // Return the existing or newly created Retrofit instance
        return retrofit;
    }
}
