package com.example.mypokedex.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.localDataBase.PokemonDao
import com.example.mypokedex.data.localDataBase.PokemonEntity
import com.example.mypokedex.data.localDataBase.PokemonRemoteMediator
import com.example.mypokedex.data.model.response.Pokemon
import com.example.mypokedex.data.model.response.PokemonDetails
import com.example.mypokedex.data.model.response.ResultPokemon
import com.example.mypokedex.domain.repo.PokemonRepository
import com.example.mypokedex.presentation.viewModels.PokemonPagingSource
import kotlinx.coroutines.flow.Flow

class PokemonRepositoryImp(private val apiService: ApiService, private val pokemonDao: PokemonDao): PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): Pokemon{
            return apiService.getPokemonList(limit,offset)
    }

    override suspend fun getPokemonDetails(pokemonName: String): PokemonDetails {
        return apiService.getPokemonData(pokemonName)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemonFromDb(): Flow<PagingData<PokemonEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 100, enablePlaceholders = false,prefetchDistance = 2),
            remoteMediator = PokemonRemoteMediator(apiService, pokemonDao),
            pagingSourceFactory = { pokemonDao.getAllPokemon() }
        ).flow
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