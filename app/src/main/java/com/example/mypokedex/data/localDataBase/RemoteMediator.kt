package com.example.mypokedex.data.localDataBase

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.model.response.ResultPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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

        Log.d("RemoteMediator", "load called with loadType: $loadType")
        val offset = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> state.pages.sumOf { it.data.size }
        }

        return try {
            val response =  coroutineScope {
                async {
                   api.getPokemonList(limit = state.config.pageSize, offset = offset)
                }.await()
            }
            val pokemonList = response.results

            Log.d("RemoteMediator", "loaded response: $response")

            val pokemonEntities = withContext(Dispatchers.IO) {
                pokemonList.map { pokemon ->
                    val details = api.getPokemonData(pokemon.name)
                    PokemonEntity(
                        name = pokemon.name,
                        url = pokemon.url,
                        types = details.types.map { it.type.name }
                    )
                }

            }
            Log.d("RemoteMediator", "Fetched ${pokemonEntities.size} pokemons from API")

            try {
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.pokemonDao().clearAll()
                    }
                    database.pokemonDao().insertAll(pokemonEntities)
                    Log.d("RemoteMediator", "Inserted ${pokemonEntities.size} pokemons into DB")

                }
            } catch (e: Exception) {
                Log.e("RemoteMediator", "Error inserting to DB", e)
            }

            MediatorResult.Success(endOfPaginationReached = response.results.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
