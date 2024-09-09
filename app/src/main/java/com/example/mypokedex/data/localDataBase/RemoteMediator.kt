package com.example.mypokedex.data.localDataBase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.model.response.PokemonDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val apiService: ApiService,
    private val pokemonDao: PokemonDao
) : RemoteMediator<Int, PokemonEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    val nextPage = lastItem?.let { (state.config.pageSize / 2) + 1 } ?: 1
                    nextPage
                }
            }

            val response = apiService.getPokemonList(
                limit = state.config.pageSize,
                offset = (page - 1) * state.config.pageSize
            )

            val pokemonEntities = withContext(Dispatchers.IO) {
                response.results.map { pokemon ->

                    val details:PokemonDetails = apiService.getPokemonData(pokemon.name)
                    PokemonEntity(
                        name = pokemon.name,
                        url = pokemon.url,
                        types = details.types.map { it.type.name }
                    )
                }
            }


            pokemonDao.insertAll(pokemonEntities)

            MediatorResult.Success(
                endOfPaginationReached = response.results.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
