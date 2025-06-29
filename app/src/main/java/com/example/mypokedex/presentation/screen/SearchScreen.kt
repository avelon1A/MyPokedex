package com.example.mypokedex.presentation.screen

import android.R.attr.contentDescription
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mypokedex.R
import com.example.mypokedex.presentation.common.BottomNavigation
import com.example.mypokedex.presentation.common.PokemonCardNew
import com.example.mypokedex.presentation.navigation.Screen
import com.example.mypokedex.presentation.viewModels.SearchViewModel
import com.example.mypokedex.util.extractPokemonNumberFromUrl

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchResults by viewModel.pokemonList.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.1f))
    ) {
        SearchBar(
            query = query,
            onQueryChange = {
                query = it
                if (it.isEmpty()) {
                    viewModel.clearSearchResults()
                } else {
                    viewModel.searchPokemon(it)
                }
            },
            onSearch = {
                viewModel.searchPokemon(query)
                active = false
            },
            active = false,
            onActiveChange = { active = it },
            placeholder = { Text("Search Pokémon") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White)
        ) {}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                if (searchResults.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(100.dp),
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "Search Icon",
                        )
                        Text(
                            text = "Search for Pokémon",
                            modifier = Modifier.padding(top = 8.dp),
                            color = Color.Gray
                        )
                    }

                } else {
                    Box {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(18.dp)
                        ) {
                            items(searchResults.size) { pokemonIndex ->
                                searchResults[pokemonIndex]?.let { pokemon ->
                                    val pokemonNumber = extractPokemonNumberFromUrl(pokemon.url)

                                    PokemonCardNew(
                                        pokemonName = pokemon.name,
                                        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonNumber}.png",
                                        onClick = {
                                            navController.navigate(
                                                Screen.Header(
                                                    pokemonName = pokemonNumber!!,
                                                    image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonNumber}.png"
                                                )
                                            )
                                        },
                                        type = pokemon.types,
                                        modifier = Modifier,
                                        sharedTransitionScope = sharedTransitionScope,
                                        animatedContentScope = animatedContentScope,
                                    )
                                }
                            }
                        }



                    }

                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 50.dp)
                ) {
                    BottomNavigation(
                        modifier = Modifier,
                        selectedIndex = 1,
                        onItemSelected = {
                            when (it) {
                                0 -> navController.navigate(Screen.HomeScreen)
                                1 -> navController.navigate(Screen.SearchScreen)
                            }
                        })
                }
            }
        }
    }
}
