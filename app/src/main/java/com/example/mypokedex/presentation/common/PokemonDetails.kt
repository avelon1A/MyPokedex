package com.example.mypokedex.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
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
fun PokemonDetailsHW(modifier: Modifier = Modifier, text: String = "", value: String= "", type: String = "") {
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

