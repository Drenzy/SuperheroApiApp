package com.example.superhero.service;

import com.example.superhero.model.Superhero;
import com.example.superhero.repository.SuperheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class SuperheroService {
    @Autowired
    SuperheroRepository superheroRepository;

    public List<Superhero> getAllSuperheros() {
        List<Superhero> superheroes = new ArrayList<>();
        superheroRepository.findAll().forEach(superheroes::add);
        return superheroes;
    }

    public List<Superhero.Brand> getAllBrands() {
        return Arrays.asList(Superhero.Brand.values());
    }

    public Superhero getSuperheroById(int id) {
        return superheroRepository.findById(id).get();
    }

    public void createSuperhero(Superhero superhero) {
        superheroRepository.save(superhero);
    }

    public void deleteSuperheroByid(int id) {
        superheroRepository.deleteById(id);
    }

    public void updateSuperheroById(int id, Superhero superhero) {
        Superhero updsuperhero = new Superhero();

        updsuperhero.setName(superhero.getName());
        updsuperhero.setSecret_identity(superhero.getSecret_identity());
        updsuperhero.setAge(superhero.getAge());
        updsuperhero.setBrand(superhero.getBrand());

        updsuperhero.setId(id);

        superheroRepository.save(updsuperhero);
    }
}
