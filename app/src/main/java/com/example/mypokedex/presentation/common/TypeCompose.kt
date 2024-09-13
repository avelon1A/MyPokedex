package com.example.mypokedex.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PokemonTypeView(modifier: Modifier = Modifier, type: String, ) {
    val pokemonBackgroundImage = getPokemonSmallBackground(type)
    val color = getTypeColor(type)
    Column(
        verticalArrangement = Arrangement.spacedBy(5.803922653198242.dp, Alignment.CenterVertically),
        modifier = modifier
            .clip(shape = RoundedCornerShape(48.607852935791016.dp))
            .background(color =color)
            .padding(
                horizontal = 6.dp,
                vertical = 2.901961326599121.dp
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.803922653198242.dp, Alignment.Start),
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
                    painter = painterResource(id = pokemonBackgroundImage),
                    contentDescription = "grass",
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
