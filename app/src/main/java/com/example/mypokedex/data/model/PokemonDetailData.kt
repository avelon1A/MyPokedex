package com.example.mypokedex.data.model

import com.example.mypokedex.data.model.response.EvolutionChainResponse
import com.example.mypokedex.data.model.response.PokemonDetails
import com.example.mypokedex.data.model.response.PokemonSpeciesResponse

data class PokemonDetailData(
    val pokemonEvolution: EvolutionChainResponse? = null,
    val pokemonDetails: PokemonDetails? = null,
    val pokemonWeekNess: List<String> = emptyList(),
    val genderRate: PokemonSpeciesResponse? = null,
    val pokemonDescription: String = "",
    val pokemonSpeciesResponse: PokemonSpeciesResponse? = null
)

