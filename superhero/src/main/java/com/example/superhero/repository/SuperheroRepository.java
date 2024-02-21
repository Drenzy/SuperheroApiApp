package com.example.superhero.repository;

import com.example.superhero.model.Superhero;
import org.springframework.data.repository.CrudRepository;

public interface SuperheroRepository extends CrudRepository<Superhero,Integer> {
}
