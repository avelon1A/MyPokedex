package com.example.mypokedex.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Screen {
    @Serializable
    data object HomeScreen : Screen()
    @Serializable
    data object OnBoardingScreen : Screen()
    @Serializable
    data object SplashScreen : Screen()
    @Serializable
    data class Header(
        val pokemonName: Int,
        val image: String
    ) : Screen()
}
