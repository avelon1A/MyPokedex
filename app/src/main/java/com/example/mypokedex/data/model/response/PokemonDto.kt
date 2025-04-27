package com.example.mypokedex.data.model.response

data class PokemonDto(
    val  name: String,
    val url: String,
    val types: List<String>
)
