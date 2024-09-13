package com.example.mypokedex.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import com.example.mypokedex.R


@Composable
fun PokemonCardNew(modifier: Modifier = Modifier, image:String, pokemonName: String, onClick: () -> Unit,type:List<String>) {
    val context = LocalContext.current
    val imageLoader = remember { ImageLoader.Builder(context).build() }
    val dominantColor = remember { mutableStateOf(Color.Transparent) }
    val dominantColortwo = remember { mutableStateOf(Color.Transparent) }
    val pokemonBackgroundImage = getPokemonBackground(type[0])
    val pokemonSmallBackground = getPokemonSmallBackground(type[0])
    var pokemonSmallBackgroundTwo = 0
    if (type.size > 1){
        pokemonSmallBackgroundTwo = getPokemonSmallBackground(type[1])

    }


    LaunchedEffect(image) {
//        dominantColor.value = getDominantColorFromImage(context,image , imageLoader)
        dominantColor.value = getTypeColor(type[0])
        if (type.size > 1){
            dominantColortwo.value = getTypeColor(type[1])

        }


    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 102.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(dominantColor.value.copy(alpha = 0.2f))
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .requiredWidth(width = 126.dp)
                .requiredHeight(height = 102.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .offset(
                        x = 0.dp,
                        y = 0.dp
                    )
                    .requiredWidth(width = 126.dp)
                    .requiredHeight(height = 102.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .background(dominantColor.value))
            Image(
                painter = painterResource(id = pokemonBackgroundImage),
                contentDescription = "Elemento Outline",
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(
                        x = 16.dp,
                        y = 4.dp
                    )
                    .requiredSize(size = 94.dp))
            Property1Bulbasaur(
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .offset(
                        x = 0.dp,
                        y = 0.dp
                    )
                ,image = image,)
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 16.dp,
                    y = 12.dp
                )
        ) {
            Column() {
                Text(
                    text = "Nº001",
                    color = Color(0xff333333),
                    style = TextStyle(
                        fontSize = 12.sp)
                )
                Text(
                    text = pokemonName,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 21.sp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start)
            ) {
                PokemonTypeView( type = type[0])
                if (type.size > 1){
                    Property1Venenoso(image2 = pokemonSmallBackgroundTwo, type = type[1],color = dominantColortwo.value)
                }

            }
        }
        Property1Default(
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .padding(top = 8.dp, end = 8.dp)
        )
    }
}

@Composable
fun Property1Bulbasaur(modifier: Modifier = Modifier, image: String) {
    Box(
        modifier = modifier
            .requiredSize(size = 94.dp)
            .padding(16.dp)
    ) {
        SubcomposeAsyncImage(
            contentDescription = "image 124",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            model =  image,

            )
    }
}


@Composable
fun Property1Venenoso(modifier: Modifier = Modifier, image2: Int, type: String, color: Color) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.803921222686768.dp, Alignment.CenterVertically),
        modifier = modifier
            .clip(shape = RoundedCornerShape(48.60784149169922.dp))
            .background(color = color)
            .padding(
                horizontal = 6.dp,
                vertical = 2.901960611343384.dp
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.803921222686768.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .requiredSize(size = 20.dp)
                        .clip(shape = CircleShape)
                        .background(color = Color.White))
                Image(
                    painter = painterResource(id = image2),
                    contentDescription = "poison",
                    colorFilter = ColorFilter.tint(color),
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(
                            x = 3.6279296875.dp,
                            y = 3.62744140625.dp
                        )
                        .requiredSize(size = 13.dp))
            }
            Text(
                text = type,
                color = Color.Black,
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium)
            )
        }
    }
}

@Composable
fun Property1Default(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredSize(size = 32.dp)


    ) {
        Box(
            modifier = Modifier
                .requiredSize(size = 32.dp)
                .clip(shape = CircleShape)
                .background(color = Color.Black.copy(alpha = 0.3f))
                .border(
                    border = BorderStroke(1.5.dp, Color.White),
                    shape = CircleShape
                ))
        Image(
            painter = painterResource(id = R.drawable.pokeball),
            contentDescription = "PokeHeart",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 8.dp,
                    y = 8.dp
                )
                .requiredSize(size = 16.dp))
    }
}
