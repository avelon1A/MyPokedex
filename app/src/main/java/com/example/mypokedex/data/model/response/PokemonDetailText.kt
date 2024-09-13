package com.example.mypokedex.data.model.response


data class PokemonSpeciesResponseText(
    val flavor_text_entries: List<FlavorTextEntry>
)

data class FlavorTextEntry(
    val flavor_text: String,
    val language: Language
)

data class Language(
    val name: String
)