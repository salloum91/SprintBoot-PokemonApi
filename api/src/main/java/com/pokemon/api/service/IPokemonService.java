package com.pokemon.api.service;

import com.pokemon.api.Dto.PokemonDto;
import com.pokemon.api.model.Pokemon;

import java.util.List;

public interface IPokemonService {

    PokemonDto createPokemon(PokemonDto pokemonDto);

    List<PokemonDto> getAllPokemon();
    PokemonDto updatePokemon(int id, PokemonDto pokemonDto);
    void deletePokemon(int id);
}
