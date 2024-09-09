package com.example.mypokedex.presentation.viewModels

import android.util.Log
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<PagingData<ResultPokemon>>(PagingData.empty())
    val pokemonList = _pokemonList.asStateFlow()

    private val _pokemonData: MutableStateFlow<PokemonDetails?> = MutableStateFlow(null)
    var pokemonData = _pokemonData.asStateFlow()
        private set

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 10, enablePlaceholders = false)
            ) {
                pokemonRepository.getPokemonPagingSource()
            }.flow.cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _pokemonList.value = pagingData
                    Log.d("HomeViewModel", "Fetched Pokemon List: $pagingData")
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