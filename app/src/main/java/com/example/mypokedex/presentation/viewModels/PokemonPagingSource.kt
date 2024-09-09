package com.example.mypokedex.presentation.viewModels


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.localDataBase.PokemonEntity
import com.example.mypokedex.data.model.response.PokemonDetails
import com.example.mypokedex.data.model.response.ResultPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ResultPokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultPokemon> {
        val position = params.key ?: 0
        return try {
            val response = apiService.getPokemonList(
                limit = params.loadSize,
                offset = position
            )

            val pokemons = response.results

            val pokemonEntities = withContext(Dispatchers.IO) {
                pokemons.map { pokemon ->
                    val details = apiService.getPokemonData(pokemon.name)
                    ResultPokemon(
                        name = pokemon.name,
                        url = pokemon.url,
                        types = details.types.map { it.type.name }
                    )
                }
            }

            LoadResult.Page(
                data = pokemonEntities,
                prevKey = if (position == 0) null else position - params.loadSize,
                nextKey = if (pokemons.isEmpty()) null else position + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultPokemon>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey?.plus(1) }
    }
}

