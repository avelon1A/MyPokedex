package com.example.mypokedex.presentation.viewModels

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.model.response.ResultPokemon
import android.util.Log

class PokemonPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ResultPokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultPokemon> {
        return try {
            val offset = params.key ?: 0
            val limit = params.loadSize
            val response = apiService.getPokemonList(limit = limit, offset = offset)

            val prevKey = if (offset == 0) null else offset - limit
            val nextKey = if (response.next == null) null else offset + limit

            LoadResult.Page(
                data = response.results,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultPokemon>): Int? {
        return state.anchorPosition
    }
}
