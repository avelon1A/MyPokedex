package com.example.mypokedex.presentation.screen

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.mypokedex.R
import com.example.mypokedex.data.model.PokemonDetailData
import com.example.mypokedex.data.model.response.PokemonDetails
import com.example.mypokedex.presentation.common.EvolutionTab
import com.example.mypokedex.presentation.common.PokemonDetailsHW
import com.example.mypokedex.presentation.common.PokemonTypeView
import com.example.mypokedex.presentation.common.getIconDescibe
import com.example.mypokedex.presentation.common.getPokemonBackground
import com.example.mypokedex.presentation.common.getTypeColor
import com.example.mypokedex.presentation.viewModels.HomeViewModel
import com.example.mypokedex.presentation.viewModels.UiState
import com.example.mypokedex.util.getGender
import com.example.mypokedex.util.getGenderPrecentageMale
import com.example.mypokedex.util.getGenderPrecentagefemale
import com.example.mypokedex.util.removeNewlines

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Header(modifier: Modifier = Modifier, pokemonName: Int, viewModel: HomeViewModel, image: String, sharedTransitionScope: SharedTransitionScope,
           animatedContentScope: AnimatedContentScope
) {

    val pokemonDetail by viewModel.pokemonDetailData.collectAsState()
    var pokemonDetailData by remember { mutableStateOf(PokemonDetailData()) }
    var loading by remember { mutableStateOf(false) }
    when(pokemonDetail){
        is UiState.Error -> {
            val errorMessage = (pokemonDetail as? UiState.Error)?.message ?: "An unexpected error occurred"
            Text(text = errorMessage)
        }
        is UiState.Success -> {
            loading = false
            pokemonDetailData = (pokemonDetail as? UiState.Success<PokemonDetailData>)?.data!!
            }

        UiState.Loading -> {
            loading = true
        }
    }
    val firstType = pokemonDetailData.pokemonDetails?.types?.get(0)?.type?.name
    val pokemonBackgroundImage = firstType?.let { getPokemonBackground(it) }
    val pokemonBackgroundColor = firstType?.let { getTypeColor(it) }
    val scrollState = rememberScrollState()
    LaunchedEffect(pokemonName) {
        viewModel.getPokemonData(pokemonName)
    }
    LaunchedEffect(pokemonDetailData) {
        Log.d("pokemonDetailData", "Pokemon Description: $pokemonDetailData")
    }
    LaunchedEffect(pokemonDetailData.pokemonEvolution) {
        Log.d("pokemonEvolution", "Pokemon Description: $pokemonDetailData?.pokemonEvolution")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            if (pokemonBackgroundColor != null) {
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(y = (-130).dp)
                        .requiredSize(size = 498.dp)
                        .clip(shape = CircleShape)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    pokemonBackgroundColor,
                                    pokemonBackgroundColor.copy(alpha = 0.6f)
                                ),
                                startY = 0f,
                                endY = 1500f
                            )
                        )
                )
            }
            pokemonBackgroundImage?.let { painterResource(id = it) }?.let {
                Image(
                    painter = it,
                    contentDescription = "Elemento Outline",
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(x = 102.dp, y = 35.dp)
                        .requiredSize(size = 204.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = "Interface, Essential/Arrow",
                    modifier = Modifier
                        .requiredSize(size = 38.dp)
                        .clip(shape = RoundedCornerShape(5.dp)),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_fav_disbable),
                    contentDescription = "PokeHeart",
                    modifier = Modifier
                        .requiredSize(size = 28.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 145.dp),
                contentAlignment = Alignment.Center
            ) {
                with(sharedTransitionScope) {
                    SubcomposeAsyncImage(
                        model = image,
                        contentDescription = "bulbasaur 1",
                        modifier = Modifier
                            .requiredWidth(width = 162.dp)
                            .requiredHeight(height = 175.dp)
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(key = "image-$image"),
                                animatedVisibilityScope = animatedContentScope,
                                boundsTransform = { _, _ ->
                                    spring(dampingRatio = 0.8f, stiffness = 380f)
                                }
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        if(loading){
            Spacer(modifier = Modifier.height(200.dp))
            CircularProgressIndicator(modifier.align(Alignment.CenterHorizontally))
        }
        else{
            pokemonDetailData.pokemonDetails?.types?.let {
                pokemonDetailData.pokemonWeekNess.let { weeknessType ->
                    pokemonDetailData.genderRate?.let { genderRate ->
                        PokeBody(
                            name = pokemonDetailData.pokemonDetails!!.name,
                            type = it.map { it.type.name },
                            details = pokemonDetailData.pokemonDescription,
                            weekType = weeknessType,
                            pokemon = pokemonDetailData.pokemonDetails!!,
                            genderRate = genderRate.gender_rate
                        )
                    }
                }
            }

        }


        if (firstType != null) {
            pokemonDetailData.pokemonEvolution?.let { EvolutionTab(pokemonEvolution = it,type = firstType,pokemonName =pokemonName) }
        }
        }
    }



@Composable
fun PokeBody(name: String, type: List<String>, details: String = "",weekType:List<String>,pokemon: PokemonDetails,genderRate: Int) {
    val  itemsToShow = if(weekType.size > 4) 4 else weekType.size


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(10.dp)
    ) {
        Text(text = name, fontSize = 32.sp)

        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(type.size) {
                PokemonTypeView(type = type[it])
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text =  removeNewlines(details), modifier = Modifier.fillMaxWidth()
            , color = Color.Black.copy(0.8f)
            , overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(10.dp))
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black.copy(0.2f))
        ) {

        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
        {
            PokemonDetailsHW(text = "WEIGHT", value = pokemon.weight.toString())
            PokemonDetailsHW(text = "HEIGHT", value = pokemon.height.toString())

        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Gender", modifier = Modifier.fillMaxWidth()
            ,fontSize = 12.sp, textAlign = TextAlign.Center
            ,color =Color.Black.copy(alpha = 0.8f))

        GenderProgressBar(genderRate = genderRate)
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(18.dp)
        , horizontalArrangement = Arrangement.SpaceBetween
        ) {
           val malevalue = getGenderPrecentageMale(genderRate = genderRate)
           val femalevalue = getGenderPrecentagefemale(genderRate = genderRate)
            GenderCount(gender = "MALE",value = malevalue)
            GenderCount(gender = "FEMALE",value = femalevalue)

        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Weaknesses", modifier = Modifier.fillMaxWidth()
            ,fontSize = 18.sp, textAlign = TextAlign.Start, fontWeight = FontWeight.Bold
            ,color =Color.Black.copy(alpha = 0.8f))
        Spacer(modifier = Modifier.height(5.dp))
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(2)){

            items(itemsToShow) {
                PokemonTypeView(type = weekType[it], modifier = Modifier
                    .padding(5.dp)
                    .height(36.dp)
                    .align(Alignment.CenterHorizontally))
            }

        }


    }


}

@Composable
fun GenderCount(gender: String, value: String) {
    val icon = getIconDescibe(gender)
    Row (modifier = Modifier.height(18.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(modifier = Modifier.padding(2.dp),painter = painterResource(id = icon), contentDescription = gender)
        Text(text = value, fontSize = 12.sp, color = Color.Black.copy(0.8f))

    }

}



@Composable
fun GenderProgressBar(genderRate: Int) {
    val malePercentage = getGender(genderRate)
    val girlsPercentage = (100f - malePercentage)

    val totalPercentage = malePercentage + girlsPercentage
    val boysWeight = if (totalPercentage >= 0) malePercentage / totalPercentage else 0f
    val girlsWeight = if (totalPercentage >= 0) girlsPercentage / totalPercentage else 0f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .padding(vertical = 8.dp)
            .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(boysWeight)
                .background(color = Color(0xFF2551C3), shape = RoundedCornerShape(8.dp))
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(girlsWeight)
                .background(color = Color(0xFFFF7596), shape = RoundedCornerShape(8.dp))
        )
    }
}
