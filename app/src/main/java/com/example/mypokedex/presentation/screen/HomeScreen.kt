package com.example.mypokedex.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mypokedex.presentation.common.PokemonCard
import com.example.mypokedex.presentation.viewModels.HomeViewModel
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController : NavController, viewModel: HomeViewModel) {

    val pokemonList = viewModel.pokemonList.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor= MaterialTheme.colorScheme.background,

        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "Pokedex",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surfaceBright
                    )
                },
                navigationIcon = {},
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor =  MaterialTheme.colorScheme.background,
                            scrolledContainerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        content = { innerPadding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.SpaceAround,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items(pokemonList.itemCount) { pokemonIndex ->
                    pokemonList[pokemonIndex]?.let { pokemon ->
                        PokemonCard(
                            pokemonName = pokemon.name,
                            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonIndex + 1}.png",
                            onClick = {
                               navController.navigate(PokemonDetailScreen(
                                   pokemonName = pokemon.name,
                                   image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonIndex + 1}.png"
                               ))

                            }
                        )
                    }
                }
            }
        }
    )
}



@Serializable
object HomeScreen