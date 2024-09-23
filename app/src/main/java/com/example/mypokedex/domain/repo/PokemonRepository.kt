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

    /**
     * Retrieves a list of Pokémon with the specified limit and offset.
     *
     * @param limit The number of Pokémon to retrieve.
     * @param offset The offset from where to start retrieving the Pokémon.
     * @return A [Pokemon] object containing the list of Pokémon.
     */
    suspend fun getPokemonList(limit: Int, offset: Int): Pokemon

    /**
     * Retrieves details of a specific Pokémon by its name.
     *
     * @param pokemonName The name of the Pokémon.
     * @return A [PokemonDetails] object with details of the Pokémon.
     */
    suspend fun getPokemonDetails(pokemonName: Int): PokemonDetails

    /**
     * Fetches Pokémon from the local database using a paging mechanism.
     *
     * @return A [Flow] emitting [PagingData] of [PokemonEntity].
     */
    fun getPokemonFromDb(): Flow<PagingData<PokemonEntity>>

    /**
     * Clears the cached Pokémon data from the local database.
     */
    suspend fun clearCachedPokemon()

    /**
     * Caches a list of Pokémon entities in the local database.
     *
     * @param pokemonEntities A list of [PokemonEntity] to be cached.
     */
    suspend fun cachePokemon(pokemonEntities: List<PokemonEntity>)

    /**
     * Provides a paging source to load Pokémon data in pages.
     *
     * @return A [PagingSource] for loading paged Pokémon data.
     */
    fun getPokemonPagingSource(): PagingSource<Int, ResultPokemon>

    /**
     * Fetches the weaknesses of a Pokémon based on its type.
     *
     * @param type The type of the Pokémon.
     * @return A [TypeResponse] object containing the weaknesses of the Pokémon.
     */
    suspend fun getPokemonWeekness(type: String): TypeResponse

    /**
     * Retrieves the gender rate for a specific Pokémon by its name.
     *
     * @param pokemonName The name of the Pokémon.
     * @return A [PokemonSpeciesResponse] object containing the gender rate.
     */
    suspend fun getPokemonGenderRate(pokemonName: String): PokemonSpeciesResponse

    /**
     * Retrieves flavor text details about a Pokémon species.
     *
     * @param pokemonName The name of the Pokémon species.
     * @return A [PokemonSpeciesResponseText] object containing flavor text details.
     */
    suspend fun getPokemonDetailsText(pokemonName: String): PokemonSpeciesResponseText

    /**
     * Retrieves the evolution chain for a Pokémon species by its ID.
     *
     * @param id The ID of the evolution chain.
     * @return An [EvolutionChainResponse] object or null if not found.
     */
    suspend fun getEvolutionChain(id: Int): EvolutionChainResponse?
}
