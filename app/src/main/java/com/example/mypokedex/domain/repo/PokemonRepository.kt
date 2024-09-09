package com.example.mypokedex.domain.repo

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.mypokedex.data.localDataBase.PokemonEntity
import com.example.mypokedex.data.model.response.Pokemon
import com.example.mypokedex.data.model.response.PokemonDetails
import com.example.mypokedex.data.model.response.ResultPokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Pokemon
    suspend fun getPokemonDetails(pokemonName: String): PokemonDetails
    fun getPokemonFromDb(): Flow<PagingData<PokemonEntity>>
    suspend fun clearCachedPokemon()
    suspend fun cachePokemon(pokemonEntities: List<PokemonEntity>)
    fun getPokemonPagingSource(): PagingSource<Int, ResultPokemon>
}