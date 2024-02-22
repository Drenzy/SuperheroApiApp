package com.example.superhero.controller;

import com.example.superhero.model.Superhero;
import com.example.superhero.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/superheroes")
public class SuperheroController {
    @Autowired
    SuperheroService superheroService;

    @GetMapping
    public List<Superhero> getAllSuperheros() {
        return superheroService.getAllSuperheros();
    }

    @GetMapping("/{id}")
    Superhero getSuperheroById(@PathVariable int id) {
        return superheroService.getSuperheroById(id);
    }

    @DeleteMapping("/{id}")
    void deleteSuperheroById(@PathVariable int id) {
        superheroService.deleteSuperheroByid(id);
    }

    @PostMapping()
    void CreateSuperhero(@RequestBody Superhero superhero) {
        superheroService.createSuperhero(superhero);
    }

    @PutMapping("{id}")
    void putGamebyId(@PathVariable int id, @RequestBody Superhero superhero) {
        superheroService.updateSuperheroById(id, superhero);
    }
}
