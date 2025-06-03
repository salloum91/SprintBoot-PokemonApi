package com.pokemon.api.service;

import com.pokemon.api.Dto.PokemonDto;
import com.pokemon.api.Dto.ReviewDto;
import com.pokemon.api.model.Pokemon;
import com.pokemon.api.model.Review;
import com.pokemon.api.repositories.PokemonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements IPokemonService {

    private final PokemonRepository pokemonRepository;

    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        // Handle reviews
        if (pokemonDto.getReviews() != null) {
            List<Review> reviews = pokemonDto.getReviews().stream().map(dto -> {
                Review review = new Review();
                review.setTitle(dto.getTitle());
                review.setContent(dto.getContent());
                review.setStars(dto.getStars());
                review.setPokemon(pokemon); // set parent pokemon
                return review;
            }).collect(Collectors.toList());

            pokemon.setReviews(reviews);
        }

        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Returning DTO without reviews for simplicity here
        return new PokemonDto(savedPokemon.getName(), savedPokemon.getType(), null);
    }

    @Override
    public List<PokemonDto> getAllPokemon() {
        List<Pokemon> pokemonList = pokemonRepository.findAll();

        return pokemonList.stream().map(pokemon -> {
            PokemonDto dto = new PokemonDto();
            dto.setName(pokemon.getName());
            dto.setType(pokemon.getType());

            if (pokemon.getReviews() != null) {
                List<ReviewDto> reviewDtos = pokemon.getReviews().stream().map(review -> {
                    ReviewDto reviewDto = new ReviewDto();
                    reviewDto.setTitle(review.getTitle());
                    reviewDto.setContent(review.getContent());
                    reviewDto.setStars(review.getStars());
                    return reviewDto;
                }).collect(Collectors.toList());

                dto.setReviews(reviewDtos);
            } else {
                dto.setReviews(null);
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deletePokemon(int id) {
        if (!pokemonRepository.existsById(id)) {
            throw new RuntimeException("Pokemon with id " + id + " not found");
        }
        pokemonRepository.deleteById(id);
    }

    @Override
    public PokemonDto updatePokemon(int id, PokemonDto pokemonDto) {
        // Find existing Pokémon by ID
        Pokemon existingPokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pokemon with id " + id + " not found"));

        // Update basic fields
        existingPokemon.setName(pokemonDto.getName());
        existingPokemon.setType(pokemonDto.getType());

        // Handle reviews update:
        // Strategy: Remove old reviews and add new ones from DTO
        existingPokemon.getReviews().clear();

        if (pokemonDto.getReviews() != null) {
            List<Review> updatedReviews = pokemonDto.getReviews().stream().map(dto -> {
                Review review = new Review();
                review.setTitle(dto.getTitle());
                review.setContent(dto.getContent());
                review.setStars(dto.getStars());
                review.setPokemon(existingPokemon); // set back reference
                return review;
            }).collect(Collectors.toList());

            existingPokemon.setReviews(updatedReviews);
        }

        // Save updated Pokémon
        Pokemon updatedPokemon = pokemonRepository.save(existingPokemon);

        // Map updated entity back to DTO including reviews
        List<ReviewDto> reviewDtos = updatedPokemon.getReviews().stream().map(review -> {
            ReviewDto rDto = new ReviewDto();
            rDto.setTitle(review.getTitle());
            rDto.setContent(review.getContent());
            rDto.setStars(review.getStars());
            return rDto;
        }).collect(Collectors.toList());

        return new PokemonDto(updatedPokemon.getName(), updatedPokemon.getType(), reviewDtos);
    }

}
