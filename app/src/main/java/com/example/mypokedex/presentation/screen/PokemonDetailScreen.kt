//package com.example.mypokedex.presentation.screen
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import coil.ImageLoader
//import coil.compose.SubcomposeAsyncImage
//import com.example.mypokedex.presentation.common.getTypeColor
//import com.example.mypokedex.presentation.viewModels.HomeViewModel
//import com.example.mypokedex.util.getDominantColorFromImage
//import kotlinx.serialization.Serializable
//
//
//@Composable
//fun PokemonDetailScreen(
//    navController: NavController,
//    viewModel: HomeViewModel,
//    pokemonName: ,
//    image: String
//) {
//
//    val context = LocalContext.current
//    val imageLoader = remember { ImageLoader.Builder(context).build() }
//    val dominantColor = remember { mutableStateOf(Color.Transparent) }
//    val pokemonDetails by viewModel.pokemonData.collectAsState()
//    LaunchedEffect(image) {
//        dominantColor.value = getDominantColorFromImage(context, image, imageLoader)
//        viewModel.getPokemonData(pokemonName)
//    }
//    Box(modifier = Modifier.fillMaxSize()) {
//        Box(
//            modifier = Modifier
//                .height(300.dp)
//                .fillMaxWidth()
//                .background(dominantColor.value),
//            contentAlignment = Alignment.Center
//        ) {
//            SubcomposeAsyncImage(
//                modifier = Modifier
//                    .size(200.dp)
//                    .padding(30.dp),
//                model = image,
//                contentScale = ContentScale.Crop,
//                contentDescription = "pokemon_$pokemonName"
//            )
//        }
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(top = 300.dp)
//        ) {
//
//            pokemonDetails?.let {
//                Text(
//                    it.name,
//                    fontSize = 33.sp,
//                    color = dominantColor.value,
//                )
//            }
//            LazyRow(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(100.dp)
//            ) {
//                pokemonDetails?.types?.let { it ->
//                    items(it.size) {
//                        Type(
//                            name = pokemonDetails!!.types[it].type.name,
//                            color = Color.Red
//                        )
//                    }
//                }
//
//            }
//
//        }
//
//    }
//
//
//}
//
//
//@Composable
//private fun Type(name: String, color: Color) {
//    val bgColor = getTypeColor(name)
//    Box(
//        modifier = Modifier
//            .height(50.dp)
//            .width(80.dp)
//            .padding(5.dp)
//            .clip(RoundedCornerShape(50.dp))
//            .background(bgColor),
//        contentAlignment = Alignment.CenterEnd
//
//    ) {
//        Text(text = name, modifier = Modifier.padding(end = 8.dp))
//
//    }
//}
//
//
//@Serializable
//data class PokemonDetailScreen(
//    val pokemonName: String,
//    val image: String
//
//)