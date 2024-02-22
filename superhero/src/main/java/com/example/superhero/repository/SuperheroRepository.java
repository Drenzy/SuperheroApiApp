package com.example.superhero.repository;

import com.example.superhero.model.Superhero;
import org.springframework.data.repository.CrudRepository;

// Interface for CRUD operations on Superhero entities, extending CrudRepository
public interface SuperheroRepository extends CrudRepository<Superhero,Integer> {
}
