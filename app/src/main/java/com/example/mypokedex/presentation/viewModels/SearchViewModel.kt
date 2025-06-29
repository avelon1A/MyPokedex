package com.example.mypokedex.presentation.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.data.localDataBase.PokemonEntity
import com.example.mypokedex.domain.repo.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel( private val pokemonRepository: PokemonRepository) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<PokemonEntity>> (emptyList())
    val pokemonList: StateFlow<List<PokemonEntity>> = _searchResults.asStateFlow()


fun searchPokemon(query: String) {
    if (query.isBlank()) {
        _searchResults.value = emptyList()
        return
    }
    viewModelScope.launch {
        runCatching {
            pokemonRepository.getPokemonByName(query)
        }.onSuccess { result ->
            _searchResults.value = result
        }.onFailure {
            _searchResults.value = emptyList()
        }
    }
}

    fun clearSearchResults() {
        _searchResults.value = emptyList()
    }


}

