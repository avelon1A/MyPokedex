package com.example.mypokedex.presentation.viewModels


import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.model.response.ResultPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ResultPokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultPokemon> {
        Log.d("PokemonPagingSource", "load called with key: ${params.key}")
        return try {
            val offset = params.key ?: 0
            val limit = params.loadSize

            val response = apiService.getPokemonList(limit, offset)
            Log.d("PokemonPagingSource", "Response: $response")
            val pokemonList = response.results
            Log.d("PokemonPagingSource", "pokemonList: ${response.results}")

            val pokemonEntities = withContext(Dispatchers.IO) {
                pokemonList.map { pokemon ->
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
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (pokemonList.isEmpty()) null else offset + limit
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultPokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(state.config.pageSize)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(state.config.pageSize)
        }
    }
}
