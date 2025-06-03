package com.pokemon.api.repositories;

import com.pokemon.api.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

// used for CRUD the database
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
}
