package com.example.mypokedex.presentation.viewModels

import android.util.Log
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
import com.example.mypokedex.data.model.response.PokemonSpeciesResponse
import com.example.mypokedex.domain.repo.PokemonRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlin.math.log

class HomeViewModel(
    private val pokemonRepository: PokemonRepository,
    apiService: ApiService,
    private val database: AppDatabase
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val pokemonList1 = Pager(
        config = PagingConfig(
            pageSize = 100, enablePlaceholders = false
        ), pagingSourceFactory = {
            database.pokemonDao().getAllPokemon()
        }).flow.cachedIn(viewModelScope).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = PagingData.empty()
    )


    private val _pokemonDetailData = MutableStateFlow<UiState<PokemonDetailData>>(UiState.Loading)
    val pokemonDetailData = _pokemonDetailData.asStateFlow()

    private val _eventChannel = Channel<UiEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()


    fun getPokemonData(pokemonName: Int) {
        viewModelScope.launch {
            _pokemonDetailData.value = UiState.Loading

            try {
                val pokemonDetails = pokemonRepository.getPokemonDetails(pokemonName)

                val detailData =
                    fetchPokemonExtrasSafely(pokemonDetails.name, pokemonDetails.types[0].type.name)

                val pokemonDetailData = PokemonDetailData(
                    pokemonDetails = pokemonDetails,
                    pokemonWeekNess = detailData.weaknessList,
                    genderRate = detailData.genderData,
                    pokemonDescription = detailData.descriptionText,
                    pokemonEvolution = detailData.evolutionChain,
                    pokemonSpeciesResponse = detailData.genderData
                )

                _pokemonDetailData.value = UiState.Success(pokemonDetailData)

            } catch (e: Exception) {
                e.printStackTrace()
                _pokemonDetailData.value = UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    suspend fun fetchPokemonExtrasSafely(
        pokemonName: String, typeName: String
    ): PokemonExtras = supervisorScope {

        val weaknessDeferred = async {
            try {
                val data = pokemonRepository.getPokemonWeekness(typeName)
                extractTypeNames(data.damage_relations)
            } catch (e: Exception) {
                _eventChannel.send(UiEvent.ShowToast("Failed to load weakness data"))
                emptyList()
            }
        }

        val genderDeferred = async {
            try {
                pokemonRepository.getPokemonGenderRate(pokemonName)
            } catch (e: Exception) {
                _eventChannel.send(UiEvent.ShowToast("Failed to load gender rate"))
                throw e
            }
        }

        val descriptionDeferred = async {
            try {
                val data = pokemonRepository.getPokemonDetailsText(pokemonName)
                data.flavor_text_entries.firstOrNull()?.flavor_text.orEmpty()
            } catch (e: Exception) {
                _eventChannel.send(UiEvent.ShowToast("Failed to load description"))
                ""
            }
        }

        val genderData = genderDeferred.await()
        val evolutionChain = try {
            val evolutionId = extractIdFromUrl(genderData.evolution_chain.url)
            evolutionId?.let { pokemonRepository.getEvolutionChain(it) }
        } catch (e: Exception) {
            _eventChannel.send(UiEvent.ShowToast("Failed to load evolution chain"))
            null
        }

        PokemonExtras(
            weaknessList = weaknessDeferred.await(),
            genderData = genderData,
            descriptionText = descriptionDeferred.await(),
            evolutionChain = evolutionChain
        )
    }

    fun updatefav(  isFav: Boolean,pokemonName: String,) {
        viewModelScope.launch {
            Log.d("HomeViewModel", "updatefav called with isFav: $isFav, pokemonName: $pokemonName")
            try { pokemonRepository.updatePokemonfav(isFav, pokemonName)
            } catch (e: Exception) {
                _eventChannel.send(UiEvent.ShowToast("Failed to update favorite status"))
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

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
}

data class PokemonExtras(
    val weaknessList: List<String>,
    val genderData: PokemonSpeciesResponse,
    val descriptionText: String,
    val evolutionChain: EvolutionChainResponse?
)


sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
