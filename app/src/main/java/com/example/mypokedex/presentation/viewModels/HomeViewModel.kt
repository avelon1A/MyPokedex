package com.example.mypokedex.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mypokedex.data.localDataBase.PokemonEntity
import com.example.mypokedex.data.model.response.DamageRelations
import com.example.mypokedex.data.model.response.EvolutionChainResponse
import com.example.mypokedex.data.model.response.PokemonDetails
import com.example.mypokedex.data.model.response.PokemonSpeciesResponse
import com.example.mypokedex.data.model.response.ResultPokemon
import com.example.mypokedex.domain.repo.PokemonRepository
import com.example.mypokedex.util.extractIdFromUrl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<PagingData<PokemonEntity>>(PagingData.empty())
    val pokemonList = _pokemonList.asStateFlow()

    private val _pokemonData: MutableStateFlow<PokemonDetails?> = MutableStateFlow(null)
    var pokemonData = _pokemonData.asStateFlow()
        private set


    private val _pokemonWeeknessData: MutableStateFlow<List<String>?> = MutableStateFlow(null)
    var pokemonWeeknessData = _pokemonWeeknessData.asStateFlow()
        private set

    private val _pokemonGenderRate: MutableStateFlow<PokemonSpeciesResponse?> =
        MutableStateFlow(null)
    var pokemonGenderRate = _pokemonGenderRate.asStateFlow()
        private set

    private val _pokemonDetailString: MutableStateFlow<String?> = MutableStateFlow(null)
    var pokemonDetailString = _pokemonDetailString.asStateFlow()
        private set
    private val _pokemonEvolution: MutableStateFlow<EvolutionChainResponse?> =
        MutableStateFlow(null)
    var pokemonEvolution = _pokemonEvolution.asStateFlow()
        private set


    init {
        observePokemonFromDb()
    }
    private fun observePokemonFromDb() {
        viewModelScope.launch {
            pokemonRepository.getPokemonFromDb()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _pokemonList.value = pagingData
                    Log.d("HomeViewModel", "Fetched Pokémon from DB: $pagingData")
                }
        }
    }


    fun getPokemonData(pokemonName: Int) {
        viewModelScope.launch {
            val data = pokemonRepository.getPokemonDetails(pokemonName)
            _pokemonData.value = data
            getpokemonWeekness(data.types[0].type.name)
            getPokemonGenderRate(data.name)
            getPokemonDetailsText(data.name)

        }
    }



    private fun getpokemonWeekness(type: String) {
        viewModelScope.launch {
            val data = pokemonRepository.getPokemonWeekness(type)
            _pokemonWeeknessData.value = extractTypeNames(data.damage_relations)
        }

    }

    private fun extractTypeNames(damageRelations: DamageRelations): List<String> {
        val typeNames = mutableListOf<String>()

        typeNames.addAll(damageRelations.double_damage_from.map { it.name })
        typeNames.addAll(damageRelations.half_damage_from.map { it.name })
        typeNames.addAll(damageRelations.no_damage_from.map { it.name })

        return typeNames
    }

    private fun getPokemonGenderRate(pokemonName: String) {
        viewModelScope.launch {
            val data = pokemonRepository.getPokemonGenderRate(pokemonName)
            _pokemonGenderRate.value = data
            val pokemonNumber = extractIdFromUrl(data.evolution_chain.url)
            if (pokemonNumber != null) {
                getPokemonEvolution(pokemonNumber)
            }
        }
    }

    private fun getPokemonDetailsText(pokemonName: String) {
        viewModelScope.launch {
            val data = pokemonRepository.getPokemonDetailsText(pokemonName)
            _pokemonDetailString.value = data.flavor_text_entries[0].flavor_text
        }
    }

    private fun getPokemonEvolution(pokemonId: Int) {
        viewModelScope.launch {
            val data = pokemonRepository.getEvolutionChain(pokemonId)
            _pokemonEvolution.value = data
        }
    }
}