package com.example.mypokedex.domain.repo

import com.example.mypokedex.data.model.response.Pokemon
import com.example.mypokedex.data.model.response.PokemonDetails

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Pokemon
    suspend fun getPokemonDetails(pokemonName: String): PokemonDetails
}