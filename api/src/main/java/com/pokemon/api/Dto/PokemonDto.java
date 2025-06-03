package com.pokemon.api.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonDto {
    private String name;
    private String type;
    private List<ReviewDto> reviews;
}