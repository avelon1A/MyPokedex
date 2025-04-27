package com.example.mypokedex.presentation.common

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypokedex.R
import com.example.mypokedex.data.model.response.EvolutionChainResponse
import com.example.mypokedex.util.countTotalEvolutions

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
            ,border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                , verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp), contentAlignment = Alignment.Center){
                    CardEvoluo(
                        Modifier,name = pokemonEvolution.chain.species.name
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
                        CardEvoluo(
                            Modifier,name = pokemonEvolution.chain.evolves_to[0].species.name
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
                        CardEvoluo(
                            Modifier,name =  pokemonEvolution.chain.evolves_to[0].evolves_to[0].species.name,
                            image = pokemonEvolution.chain.evolves_to[0].evolves_to[0].species.url,
                            type = type)
                    }

                }

            }

        }

    }

}
