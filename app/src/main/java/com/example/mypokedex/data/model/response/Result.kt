package com.example.mypokedex.data.model.response

data class ResultPokemon(
    val name: String,
    val url: String,
    var types: List<String> = emptyList()
)
