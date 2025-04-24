package com.example.mypokedex.data.localDataBase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.model.response.PokemonDetails
import com.example.mypokedex.data.model.response.ResultPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val api: ApiService,
    private val database: AppDatabase
) : RemoteMediator<Int, PokemonEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        val offset = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) return MediatorResult.Success(true)
                state.pages.sumOf { it.data.size }
            }
        }

        return try {
            val response = api.getPokemonList(limit = state.config.pageSize, offset = offset)
            val entities = response.results.map { PokemonEntity(name = it.name, url = it.url, types = it.types) }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.pokemonDao().clearAll()
                }
                database.pokemonDao().insertAll(entities)
            }

            MediatorResult.Success(endOfPaginationReached = response.results.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
