package com.example.mypokedex.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.mypokedex.util.extractPokemonNumberFromUrl
import com.example.mypokedex.util.extractPokemonNumberFromUrl2

@Composable
fun CardEvoluo(modifier: Modifier = Modifier,name: String,image: String,type: String) {

    val pokemonNumber = extractPokemonNumberFromUrl2(image)

    val pokemonBackgroundImage = getPokemonBackground(type)
    val pokemonBackgroundColor = getTypeColor(type)
    Surface(
        shape = CircleShape,
        border = BorderStroke(1.dp, Color(0xffe6e6e6)),
        modifier = Modifier
            .clip(shape = CircleShape).background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .requiredWidth(width = 296.dp)
                .requiredHeight(height = 76.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                modifier = Modifier
                    .align(alignment = Alignment.Center)

            ) {
                Column {
                   Text(text = name, fontSize = 18.sp)

                }

            }
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(
                        x = 0.dp,
                        y = 1.dp
                    )
                    .requiredWidth(width = 96.dp)
                    .requiredHeight(height = 74.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(
                            x = 1.dp,
                            y = 0.dp
                        )
                        .requiredWidth(width = 95.dp)
                        .requiredHeight(height = 74.dp)
                        .clip(shape = RoundedCornerShape(71.dp))
                        .background(color = pokemonBackgroundColor))
                Image(
                    painter = painterResource(id = pokemonBackgroundImage),
                    contentDescription = "Elemento Outline",
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(
                            x = 16.dp,
                            y = 4.dp
                        )
                        .requiredSize(size = 65.dp))

            }
            if (pokemonNumber != null) {
                Property1Bulbasaur(
                    modifier = modifier
                        .align(alignment = Alignment.TopStart), image = pokemonNumber
                )
            }
        }
    }
}






@Composable
fun Property1Bulbasaur(modifier: Modifier = Modifier,image:Int) {
    Box(
        modifier = modifier
            .requiredWidth(width = 108.dp)
            .requiredHeight(height = 81.dp)
    ) {
        SubcomposeAsyncImage(
        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${image}.png",
            contentDescription = "image 117",
            modifier = Modifier
                .fillMaxSize())
    }
}

