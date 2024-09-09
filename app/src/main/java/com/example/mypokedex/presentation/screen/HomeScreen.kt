package com.example.mypokedex.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mypokedex.presentation.common.PokemonCard
import com.example.mypokedex.presentation.common.PokemonCardNew
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
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(18.dp)
            ) {
                items(pokemonList.itemCount) { pokemonIndex ->
                    pokemonList[pokemonIndex]?.let { pokemon ->
                        val pokemonNumber = extractPokemonNumberFromUrl(pokemon.url)

                        PokemonCardNew(
                            pokemonName = pokemon.name,
                            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonNumber}.png",
                            onClick = {
                               navController.navigate(PokemonDetailScreen(
                                   pokemonName = pokemon.name,
                                   image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonNumber}.png"
                               ))

                            }
                            , type = pokemon.types
                        )
                    }
                }
            }
        }
    )
}
fun extractPokemonNumberFromUrl(url: String): Int? {
    val regex = """pokemon/(\d+)/""".toRegex()
    val matchResult = regex.find(url)
    return matchResult?.groups?.get(1)?.value?.toIntOrNull()
}


@Serializable
object HomeScreen