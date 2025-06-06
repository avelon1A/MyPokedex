package com.example.mypokedex.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.localDataBase.AppDatabase
import com.example.mypokedex.data.model.PokemonDetailData
import com.example.mypokedex.data.model.response.DamageRelations
import com.example.mypokedex.data.model.response.EvolutionChainResponse
import com.example.mypokedex.domain.repo.PokemonRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonRepository: PokemonRepository,
    apiService: ApiService,
    private val database: AppDatabase
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val pokemonList1 = Pager(
        config = PagingConfig(
            pageSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            database.pokemonDao().getAllPokemon()
        }
    ).flow
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PagingData.empty()
        )


private val _pokemonDetailData = MutableStateFlow<UiState<PokemonDetailData>>(UiState.Loading)
    val pokemonDetailData = _pokemonDetailData.asStateFlow()

    fun getPokemonData(pokemonName: Int) {
        viewModelScope.launch {
            _pokemonDetailData.value = UiState.Loading
            try {
                val pokemonDetails = pokemonRepository.getPokemonDetails(pokemonName)


                val weaknessDeferred = async { pokemonRepository.getPokemonWeekness(pokemonDetails.types[0].type.name) }
                val genderDeferred = async { pokemonRepository.getPokemonGenderRate(pokemonDetails.name) }
                val descriptionDeferred = async { pokemonRepository.getPokemonDetailsText(pokemonDetails.name) }

                val weaknessData = weaknessDeferred.await()
                val genderData = genderDeferred.await()
                val descriptionData = descriptionDeferred.await()

                val weaknessList = extractTypeNames(weaknessData.damage_relations)
                val descriptionText = descriptionData.flavor_text_entries.firstOrNull()?.flavor_text.orEmpty()

                var evolutionChain: EvolutionChainResponse? = null
                val evolutionChainId = extractIdFromUrl(genderData.evolution_chain.url)
                if (evolutionChainId != null) {
                    evolutionChain = pokemonRepository.getEvolutionChain(evolutionChainId)
                }

                val pokemonDetailData = PokemonDetailData(
                    pokemonDetails = pokemonDetails,
                    pokemonWeekNess = weaknessList,
                    genderRate = genderData,
                    pokemonDescription = descriptionText,
                    pokemonEvolution = evolutionChain,
                    pokemonSpeciesResponse = genderData
                )

                _pokemonDetailData.value = UiState.Success(pokemonDetailData)

            } catch (e: Exception) {
                e.printStackTrace()
                _pokemonDetailData.value = UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    private fun extractTypeNames(damageRelations: DamageRelations): List<String> {
        val typeNames = mutableListOf<String>()
        typeNames.addAll(damageRelations.double_damage_from.map { it.name })
        typeNames.addAll(damageRelations.half_damage_from.map { it.name })
        typeNames.addAll(damageRelations.no_damage_from.map { it.name })
        return typeNames
    }

    private fun extractIdFromUrl(url: String): Int? {
        return url.trimEnd('/').split("/").lastOrNull()?.toIntOrNull()
    }
}
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
