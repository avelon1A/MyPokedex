package com.example.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.mypokedex.presentation.common.PokemonCard
import com.example.mypokedex.presentation.navigation.AppNavHost
import com.example.mypokedex.presentation.screen.HomeScreen
import com.example.mypokedex.presentation.screen.OnBoardingScreen
import com.example.mypokedex.presentation.ui.theme.MyPokedexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
               false
            }
        }
        setContent {
            val navController = rememberNavController()
            MyPokedexTheme {
                AppNavHost(
                    navController = navController,
                    modifier = Modifier.padding(),
                    startDestination = OnBoardingScreen
                )

            }
        }
    }
}

