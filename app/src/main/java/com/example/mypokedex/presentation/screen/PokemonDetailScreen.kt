package com.example.mypokedex.presentation.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.mypokedex.R
import com.example.mypokedex.data.model.response.EvolutionChainResponse
import com.example.mypokedex.data.model.response.PokemonDetails
import com.example.mypokedex.presentation.common.CardEvoluo
import com.example.mypokedex.presentation.common.PokemonTypeView
import com.example.mypokedex.presentation.common.getIconDescibe
import com.example.mypokedex.presentation.common.getPokemonBackground
import com.example.mypokedex.presentation.common.getTypeColor
import com.example.mypokedex.presentation.viewModels.HomeViewModel
import com.example.mypokedex.util.countTotalEvolutions
import com.example.mypokedex.util.getGender
import com.example.mypokedex.util.getGenderPrecentageMale
import com.example.mypokedex.util.getGenderPrecentagefemale
import com.example.mypokedex.util.removeNewlines
import kotlinx.serialization.Serializable

@Composable
fun Header(modifier: Modifier = Modifier,  pokemonName: Int, viewModel: HomeViewModel,image: String) {

    val pokemonDetails by viewModel.pokemonData.collectAsState()
    val pokemonWeekNess by viewModel.pokemonWeeknessData.collectAsState()
    val genderRate by viewModel.pokemonGenderRate.collectAsState()
    val pokemonDescription by viewModel.pokemonDetailString.collectAsState()
    val pokemonEvolution by viewModel.pokemonEvolution.collectAsState()
    val firstType = pokemonDetails?.types?.get(0)?.type?.name

    val pokemonBackgroundImage = firstType?.let { getPokemonBackground(it) }
    val pokemonBackgroundColor = firstType?.let { getTypeColor(it) }
    val scrollState = rememberScrollState()
    LaunchedEffect(pokemonName) {
        viewModel.getPokemonData(pokemonName)
    }
    LaunchedEffect(pokemonDescription) {
        Log.d("Header", "Pokemon Description: $pokemonDescription")
    }
    LaunchedEffect(pokemonEvolution) {
        Log.d("pokemonEvolution", "Pokemon Description: $pokemonEvolution")
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
                SubcomposeAsyncImage(
                    model = image,
                    contentDescription = "bulbasaur 1",
                    modifier = Modifier
                        .requiredWidth(width = 162.dp)
                        .requiredHeight(height = 175.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        pokemonDetails?.types?.let {
            pokemonWeekNess?.let { weeknessType ->
                genderRate?.let { genderRate ->
                    pokemonDescription?.let { it1 ->
                        PokeBody(
                            name = pokemonDetails!!.name,
                            type = it.map { it.type.name },
                            details = it1,
                            weekType = weeknessType,
                            pokemon = pokemonDetails!!,
                            genderRate = genderRate.gender_rate
                        )
                    }
                    }
                }
            }

        if (firstType != null) {
            pokemonEvolution?.let { EvolutionTab(pokemonEvolution = it,type = firstType,pokemonName =pokemonName) }
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
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
//        {
//            PokemonDetailsHW(text = "CATEGORY", value = category)
//            PokemonDetailsHW(text = "ABILITY", value = ability)
//
//        }
//        Spacer(modifier = Modifier.height(20.dp))
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
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(2),){

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






@Composable
fun PokemonDetailsHW(modifier: Modifier = Modifier, text: String = "", value: String= "",type: String = "") {
    val icon = getIconDescibe(text)
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "la:weight-hanging",
                colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.6f)),
                modifier = Modifier
                    .requiredSize(size = 16.dp)
            )
            Text(
                text = text,
                color = Color.Black.copy(alpha = 0.6f),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .requiredWidth(width = 154.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .border(
                    border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(all = 8.dp)
        ) {
            Text(
                text = value,
                color = Color.Black.copy(alpha = 0.9f),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun EvolutionTab(pokemonEvolution: EvolutionChainResponse, type: String, pokemonName: Int){
   val noOFEvolution = countTotalEvolutions(pokemonEvolution.chain)

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(417.dp)
        .background(Color.White)
        , verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Evolution",fontSize = 18.sp, fontWeight = FontWeight.Bold)
        OutlinedCard(modifier = Modifier
            .height(382.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.White)
            ,border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.1f))) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
            , verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

              Box(modifier = Modifier
                  .fillMaxWidth()
                  .height(76.dp), contentAlignment = Alignment.Center){
                  CardEvoluo(Modifier,name = pokemonEvolution.chain.species.name
                      ,image = pokemonEvolution.chain.species.url,
                      type = type)
                  }


                if(noOFEvolution >0){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp), contentAlignment = Alignment.Center){

                        Row {
                            Image(painter = painterResource(id = R.drawable.icon_evolution_down), contentDescription = "next")
                            Text(text = "Level ${pokemonEvolution.chain.evolves_to[0].evolution_details[0].min_level}", color = Color(0xff173EA5), fontSize = 12.sp)
                        }

                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(76.dp),
                        contentAlignment = Alignment.Center
                        ){
                        CardEvoluo(Modifier,name = pokemonEvolution.chain.evolves_to[0].species.name
                            ,image = pokemonEvolution.chain.evolves_to[0].species.url,
                            type = type)
                    }
                }

                if (noOFEvolution >1){

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp), contentAlignment = Alignment.Center){

                        Row {
                            Image(painter = painterResource(id = R.drawable.icon_evolution_down), contentDescription = "next")
                            Text(text = "Level ${pokemonEvolution.chain.evolves_to[0].evolves_to[0].evolution_details[0].min_level}", color = Color(0xff173EA5), fontSize = 12.sp)
                        }

                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(76.dp),
                        contentAlignment = Alignment.Center

                    ){
                        CardEvoluo(Modifier,name =  pokemonEvolution.chain.evolves_to[0].evolves_to[0].species.name,
                            image = pokemonEvolution.chain.evolves_to[0].evolves_to[0].species.url,
                            type = type)
                    }

                }

            }

        }

    }

}


@Serializable
data class Header(
    val pokemonName: Int,
    val image: String

)