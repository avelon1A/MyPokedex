package com.example.mypokedex.data.repo

import androidx.paging.PagingSource
import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.localDataBase.PokemonDao
import com.example.mypokedex.data.localDataBase.PokemonEntity
import com.example.mypokedex.data.model.response.EvolutionChainResponse
import com.example.mypokedex.data.model.response.Pokemon
import com.example.mypokedex.data.model.response.PokemonDetails
import com.example.mypokedex.data.model.response.PokemonSpeciesResponse
import com.example.mypokedex.data.model.response.PokemonSpeciesResponseText
import com.example.mypokedex.data.model.response.ResultPokemon
import com.example.mypokedex.data.model.response.TypeResponse
import com.example.mypokedex.domain.repo.PokemonRepository
import com.example.mypokedex.presentation.viewModels.PokemonPagingSource

class PokemonRepositoryImp(private val apiService: ApiService, private val pokemonDao: PokemonDao): PokemonRepository {

    private val pokemonDetailsCache = mutableMapOf<Int, PokemonDetails>()
    private val speciesCache = mutableMapOf<String, PokemonSpeciesResponse>()
    private val speciesTextCache = mutableMapOf<String, PokemonSpeciesResponseText>()
    private val weaknessCache = mutableMapOf<String, TypeResponse>()
    private val evolutionCache = mutableMapOf<Int, EvolutionChainResponse>()

    override suspend fun getPokemonList(limit: Int, offset: Int): Pokemon {
        return apiService.getPokemonList(limit, offset)
    }

    override suspend fun getPokemonDetails(pokemonName: Int): PokemonDetails {
        return pokemonDetailsCache[pokemonName]
            ?: apiService.getPokemonData(pokemonName).also {
                pokemonDetailsCache[pokemonName] = it
            }
    }

    override suspend fun getPokemonGenderRate(pokemonName: String): PokemonSpeciesResponse {
        return speciesCache[pokemonName]
            ?: apiService.getGenderRate(pokemonName).also {
                speciesCache[pokemonName] = it
            }
    }

    override suspend fun getPokemonDetailsText(pokemonName: String): PokemonSpeciesResponseText {
        return speciesTextCache[pokemonName]
            ?: apiService.getPokemonDetails(pokemonName).also {
                speciesTextCache[pokemonName] = it
            }
    }

    override suspend fun getPokemonWeekness(type: String): TypeResponse {
        return weaknessCache[type]
            ?: apiService.getPokemonTypeByName(type).also {
                weaknessCache[type] = it
            }
    }

    override suspend fun getEvolutionChain(id: Int): EvolutionChainResponse? {
        return evolutionCache[id]
            ?: apiService.getEvolutionChain(id).body()?.also {
                evolutionCache[id] = it
            }
    }

    override suspend fun clearCachedPokemon() {
        pokemonDao.clearAll()
    }
    override suspend fun cachePokemon(pokemonEntities: List<PokemonEntity>) {
        pokemonDao.insertAll(pokemonEntities)
    }
    override fun getPokemonPagingSource(): PagingSource<Int, ResultPokemon> {
        return PokemonPagingSource(apiService)
    }
}
