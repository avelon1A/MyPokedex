package com.example.mypokedex.domain.repo

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.mypokedex.data.localDataBase.PokemonEntity
import com.example.mypokedex.data.model.response.EvolutionChainResponse
import com.example.mypokedex.data.model.response.Pokemon
import com.example.mypokedex.data.model.response.PokemonDetails
import com.example.mypokedex.data.model.response.PokemonSpeciesResponse
import com.example.mypokedex.data.model.response.PokemonSpeciesResponseText
import com.example.mypokedex.data.model.response.ResultPokemon
import com.example.mypokedex.data.model.response.TypeResponse
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Pokemon
    suspend fun getPokemonDetails(pokemonName: String): PokemonDetails
    fun getPokemonFromDb(): Flow<PagingData<PokemonEntity>>
    suspend fun clearCachedPokemon()
    suspend fun cachePokemon(pokemonEntities: List<PokemonEntity>)
    fun getPokemonPagingSource(): PagingSource<Int, ResultPokemon>
    suspend fun getPokemonWeekness(type:String): TypeResponse
    suspend fun getPokemonGenderRate(pokemonName: String): PokemonSpeciesResponse
    suspend fun getPokemonDetailsText(pokemonName: String): PokemonSpeciesResponseText
    suspend fun getEvolutionChain(id: Int): EvolutionChainResponse

}