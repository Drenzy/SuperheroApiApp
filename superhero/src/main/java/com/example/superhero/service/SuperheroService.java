package com.example.superhero.service;

import com.example.superhero.model.Superhero;
import com.example.superhero.repository.SuperheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class SuperheroService {
    // Injecting SuperheroRepository using Spring's @Autowired annotation
    @Autowired
    SuperheroRepository superheroRepository;

    // Method to get a list of all superheroes from the repository
    public List<Superhero> getAllSuperheros() {
        List<Superhero> superheroes = new ArrayList<>();
        // Using forEach to add all superheroes from the repository to the list
        superheroRepository.findAll().forEach(superheroes::add);
        return superheroes;
    }

    // Method to get a list of all superhero brands
    public List<Superhero.Brand> getAllBrands() {
        // Converting the enum values to a list
        return Arrays.asList(Superhero.Brand.values());
    }

    // Method to get a superhero by ID from the repository
    public Superhero getSuperheroById(int id) {
        // Using get method to retrieve the superhero by ID
        return superheroRepository.findById(id).get();
    }

    // Method to create a new superhero in the repository
    public void createSuperhero(Superhero superhero) {
        // Saving the new superhero using the repository's save method
        superheroRepository.save(superhero);
    }

    // Method to delete a superhero by ID from the repository
    public void deleteSuperheroByid(int id) {
        // Deleting the superhero by ID using the repository's deleteById method
        superheroRepository.deleteById(id);
    }

    // Method to update a superhero by ID in the repository
    public void updateSuperheroById(int id, Superhero superhero) {
        // Creating a new superhero object to update the existing superhero
        Superhero updsuperhero = new Superhero();

        // Setting the properties of the new superhero with the values from the updated superhero
        updsuperhero.setName(superhero.getName());
        updsuperhero.setSecret_identity(superhero.getSecret_identity());
        updsuperhero.setAge(superhero.getAge());
        updsuperhero.setBrand(superhero.getBrand());

        // Setting the ID to match the existing superhero's ID
        updsuperhero.setId(id);

        // Saving the updated superhero using the repository's save method
        superheroRepository.save(updsuperhero);
    }
}
