package com.pokemon.api.controllers;

import com.pokemon.api.Dto.PokemonDto;
import com.pokemon.api.service.IPokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final IPokemonService pokemonService;

    @Autowired
    public PokemonController(IPokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public ResponseEntity<List<PokemonDto>> getPokemon() {
        // You need to implement a `getAllPokemon()` method in your service
        return ResponseEntity.ok(pokemonService.getAllPokemon());
    }
    @PostMapping
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto) {
        PokemonDto created = pokemonService.createPokemon(pokemonDto);
        return ResponseEntity.ok(created);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePokemon(@PathVariable int id) {
        pokemonService.deletePokemon(id);
        return ResponseEntity.noContent().build();  // HTTP 204 No Content on success
    }

    @PutMapping("/{id}")
    public ResponseEntity<PokemonDto> updatePokemon(@PathVariable int id, @RequestBody PokemonDto pokemonDto) {
        PokemonDto updated = pokemonService.updatePokemon(id, pokemonDto);
        return ResponseEntity.ok(updated);
    }
}
