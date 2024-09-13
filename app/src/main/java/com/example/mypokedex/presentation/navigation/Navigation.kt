package com.example.mypokedex.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mypokedex.presentation.screen.Header
import com.example.mypokedex.presentation.screen.HomeScreen
import com.example.mypokedex.presentation.screen.OnBoardingScreen
import com.example.mypokedex.presentation.viewModels.HomeViewModel
import com.example.mypokedex.presentation.viewModels.OnBoardingViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Any
) {
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            composable<HomeScreen> {
            val viewModel: HomeViewModel = koinViewModel()
                HomeScreen(navController = navController,viewModel = viewModel)
            }

            composable<OnBoardingScreen> {
                val viewModel: OnBoardingViewModel = koinViewModel()
                OnBoardingScreen(navController = navController,event = viewModel::OnEvent)

            }
            composable<Header> {
                val viewModel: HomeViewModel = koinViewModel()
                val pokemonDetails = it.toRoute<Header>()
                Header( viewModel = viewModel, pokemonName = pokemonDetails.pokemonName, image = pokemonDetails.image)

            }

        }
    }
}