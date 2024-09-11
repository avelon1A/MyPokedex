package com.example.mypokedex.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mypokedex.R

@Composable
fun Header(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = (-0).dp,
                    y = (-227).dp
                )
                .requiredSize(size = 498.dp)
                .clip(shape = CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        0f to Color(0xff63bc5a),
                        1f to Color(0xff63bc5a).copy(alpha = 0.5f),
                        start = Offset(140.5f, 208f),
                        end = Offset(370.5f, 498f)
                    )
                ))
        Image(
            painter = painterResource(id = R.drawable.type_fire),
            contentDescription = "Elemento Outline",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 77.dp,
                    y = 35.dp
                )
                .requiredSize(size = 204.dp))
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
                colorFilter = ColorFilter.tint(Color.White),

            )
            Image(
                painter = painterResource(id = R.drawable.icon_fav_disbable),
                contentDescription = "PokeHeart",
                modifier = Modifier
                    .requiredSize(size = 28.dp),
                colorFilter = ColorFilter.tint(Color.White),
            )
        }
        Box (modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.on_boarding_two),
                contentDescription = "bulbasaur 1",
                modifier = Modifier
                    .offset
                        (y = 145.dp)
                    .requiredWidth(width = 142.dp)
                    .requiredHeight(height = 155.dp))
        }
        Column(modifier = Modifier.fillMaxSize().offset(y=300.dp)
        ) {
            Text(text = name,)

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HeaderPreview() {
    Header(Modifier)
}