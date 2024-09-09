package com.example.mypokedex.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.mypokedex.data.model.response.PokemonDetails
import com.example.mypokedex.data.model.response.ResultPokemon
import com.example.mypokedex.domain.repo.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonPagingSource: PokemonPagingSource,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonList: MutableStateFlow<PagingData<ResultPokemon>> =
        MutableStateFlow(PagingData.empty())
    var pokemonList = _pokemonList.asStateFlow()
        private set

    private val _pokemonData: MutableStateFlow<PokemonDetails?> = MutableStateFlow(null)
    var pokemonData = _pokemonData.asStateFlow()
        private set

    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 10, enablePlaceholders = true
                )
            ) {
                pokemonPagingSource
            }.flow.cachedIn(viewModelScope).collect { pagingData ->
                _pokemonList.value = pagingData.map { resultPokemon ->
                    val details = pokemonRepository.getPokemonDetails(resultPokemon.name)
                    resultPokemon.types = details.types.map { it.type.name }
                    resultPokemon
                }
            }
        }
    }

    fun getPokemonData(pokemonName: String) {
        viewModelScope.launch {
            val data = pokemonRepository.getPokemonDetails(pokemonName)
            _pokemonData.value = data
        }
    }
}