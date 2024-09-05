package com.example.mypokedex.data.model.response

data class Pokemon(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<ResultPokemon>
)