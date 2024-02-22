package com.example.superhero.controller;

import com.example.superhero.model.Superhero;
import com.example.superhero.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

// Controller class for handling Superhero-related HTTP requests
@RestController
@RequestMapping("/api/superheroes")
public class SuperheroController {

    // Injecting SuperheroService using Spring's @Autowired annotation
    @Autowired
    SuperheroService superheroService;

    // Endpoint to get a list of all superheroes
    @GetMapping
    public List<Superhero> getAllSuperheros() {
        return superheroService.getAllSuperheros();
    }

    // Endpoint to get a superhero by ID
    @GetMapping("/{id}")
    Superhero getSuperheroById(@PathVariable int id) {
        return superheroService.getSuperheroById(id);
    }

    // Endpoint to get a list of all superhero brands
    @GetMapping("/brands")
    public List<Superhero.Brand> getAllBrands() {
        return superheroService.getAllBrands();
    }

    // Endpoint to delete a superhero by ID
    @DeleteMapping("/{id}")
    void deleteSuperheroById(@PathVariable int id) {
        superheroService.deleteSuperheroByid(id);
    }

    // Endpoint to create a new superhero
    @PostMapping()
    void CreateSuperhero(@RequestBody Superhero superhero) {
        superheroService.createSuperhero(superhero);
    }

    // Endpoint to update a superhero by ID
    @PutMapping("{id}")
    void putGamebyId(@PathVariable int id, @RequestBody Superhero superhero) {
        superheroService.updateSuperheroById(id, superhero);
    }
}
