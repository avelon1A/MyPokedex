package com.example.mypokedex.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mypokedex.R

@Preview(showBackground = true)
@Composable
fun SplashScreen() {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF000029)),contentAlignment = Alignment.Center ){
        Image(modifier = Modifier.height(71.dp).width(192.dp), painter = painterResource(id = R.drawable.splach_icon), contentDescription = "splash_screen")

    }

}
