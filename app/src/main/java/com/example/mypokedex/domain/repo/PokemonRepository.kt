package com.example.mypokedex.domain.repo

import com.example.mypokedex.data.model.response.Pokemon

interface PokemonRepository {
    suspend fun getPokemonList(page: Int, limit: Int): Pokemon

}