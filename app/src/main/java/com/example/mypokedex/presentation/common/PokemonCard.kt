package com.example.mypokedex.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import com.example.mypokedex.util.getDominantColorFromImage


@Composable
fun PokemonCard(pokemonName: String, image: String, onClick: () -> Unit) {
    val context = LocalContext.current
    val imageLoader = remember { ImageLoader.Builder(context).build() }
    val dominantColor = remember { mutableStateOf(Color.Transparent) }


    LaunchedEffect(image) {
        dominantColor.value = getDominantColorFromImage(context,image , imageLoader)
    }
    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(dominantColor.value)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize().padding(30.dp),
                model =  image,
                contentScale = ContentScale.Crop,
                contentDescription = "pokemon_$pokemonName"
            )
    }
}
@Preview
@Composable
fun PokemonCardPreview() {
    PokemonCard(
        pokemonName = "Pikachu",
        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
        onClick = {

        },
    )
}