package com.example.mypokedex.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mypokedex.data.model.response.ResultPokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonPagingSource: PokemonPagingSource

):ViewModel() {

    private val _pokemonList: MutableStateFlow<PagingData<ResultPokemon>> =
        MutableStateFlow(PagingData.empty())
    var pokemonList = _pokemonList.asStateFlow()
        private set

    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    10, enablePlaceholders = true
                )
            ) {
                pokemonPagingSource
            }.flow.cachedIn(viewModelScope).collect {
                _pokemonList.value = it
            }
        }
    }

}