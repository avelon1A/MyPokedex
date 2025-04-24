package com.example.mypokedex.presentation.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mypokedex.presentation.common.PokemonCardNew
import com.example.mypokedex.presentation.viewModels.HomeViewModel
import com.example.mypokedex.util.extractPokemonNumberFromUrl
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController : NavController, viewModel: HomeViewModel) {

//    val pokemonList = viewModel.pokemonList.collectAsLazyPagingItems()
    val pokemonList1 = viewModel.pokemonList1.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
//    Log.d("HomeScreen", "Pokemon List: $pokemonList")
//    LaunchedEffect(pokemonList.itemCount) {
//        Log.d("HomeScreen", "Pokemon List Size: ${pokemonList.itemCount}")
//        pokemonList.itemSnapshotList.forEach { item ->
//            Log.d("HomeScreen", "Pokemon Item: $item")
//        }
//    }

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
            if(pokemonList1.itemCount == 0){
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp)
                        .padding(bottom = 100.dp).background(Color.Green),color = Color.Green)

                }

        }
            else{
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp)
            ) {
                items(pokemonList1.itemCount) { pokemonIndex ->
                    pokemonList1[pokemonIndex]?.let { pokemon ->
                        val pokemonNumber = extractPokemonNumberFromUrl(pokemon.url)

                        PokemonCardNew(
                            pokemonName = pokemon.name,
                            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonNumber}.png",
                            onClick = {
                                navController.navigate(
                                    Header(
                                    pokemonName = pokemonNumber!!,
                                    image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonNumber}.png"
                                )
                                )

                            }
                            , type = pokemon.types
                        )
                    }
                }
            }
        }

        }
    )
}



@Serializable
object HomeScreen