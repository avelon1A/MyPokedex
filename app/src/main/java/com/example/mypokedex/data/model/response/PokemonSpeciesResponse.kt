package com.example.mypokedex.data.model.response


data class PokemonSpeciesResponse(
    val id: Int,
    val name: String,
    val gender_rate: Int,
    val evolution_chain: EvolutionChainUrl
)
data class EvolutionChainUrl(
    val url: String
)