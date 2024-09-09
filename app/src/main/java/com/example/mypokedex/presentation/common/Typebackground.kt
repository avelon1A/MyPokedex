package com.example.mypokedex.presentation.common

import androidx.compose.ui.graphics.Color
import com.example.mypokedex.R

fun getTypeColor(name: String): Color {
    return when (name.lowercase()) {
        "normal" -> Color(0xFF919AA2)
        "fire" -> Color(0xFFFF9D55)
        "water" -> Color(0xFF5090D6)
        "electric" -> Color(0xFFF4D23C)
        "grass" -> Color(0xFF63BC5A)
        "ice" -> Color(0xFF89AAE3)
        "fighting" -> Color(0xFFCE416B)
        "poison" -> Color(0xFFB567CE)
        "ground" -> Color(0xFFD97845)
        "flying" -> Color(0xFF89AAE3)
        "psychic" -> Color(0xFFEC8FE6)
        "bug" -> Color(0xFF91C12F)
        "rock" -> Color(0xFFC5B78C)
        "ghost" -> Color(0xFF5269AD)
        "dragon" -> Color(0xFF0B6DC3)
        "dark" -> Color(0xFF5A5465)
        "steel" -> Color(0xFF5A8EA2)
        "fairy" -> Color(0xFFEC8FE6)
        else -> Color.Gray
    }
}
fun getPokemonBackground(name:String): Int {
    return when (name.lowercase()) {
        "normal" -> R.drawable.type_normal
        "fire" -> R.drawable.type_fire
        "water" -> R.drawable.type_water
        "electric" ->R.drawable.type_eletric
        "grass" -> R.drawable.type_grass
        "ice" -> R.drawable.type_ice
        "fighting" -> R.drawable.type_fighting
        "poison" -> R.drawable.type_poision
        "ground" -> R.drawable.type_ground
        "flying" -> R.drawable.type_flying
        "psychic" -> R.drawable.type_psysic
        "bug" -> R.drawable.type_bug
        "rock" -> R.drawable.type_rock
        "ghost" -> R.drawable.type_gost
        "dragon" -> R.drawable.type_dragon
        "dark" -> R.drawable.type_night
        "steel" -> R.drawable.type_metal
        "fairy" -> R.drawable.type_fairy
        else ->  R.drawable.pokeball
    }
}
fun getPokemonSmallBackground(name:String): Int {
    return when (name.lowercase()) {
        "normal" -> R.drawable.icon_normal_type
        "fire" -> R.drawable.icon_fire_type
        "water" -> R.drawable.icon_water_type
        "electric" ->R.drawable.icon_eletric_type
        "grass" -> R.drawable.icon_grass_type
        "ice" -> R.drawable.icon_ice_type
        "fighting" -> R.drawable.icon_fighting_type
        "poison" -> R.drawable.icon_posion_type
        "ground" -> R.drawable.icon_groung_type
        "flying" -> R.drawable.icon_flying_type
        "psychic" -> R.drawable.icon_psysic_type
        "bug" -> R.drawable.icon_bug_type
        "rock" -> R.drawable.icon_stone_type
        "ghost" -> R.drawable.icon_gost_type
        "dragon" -> R.drawable.icon_dragon_type
        "dark" -> R.drawable.icon_dark_type
        "steel" -> R.drawable.icon_steel_type
        "fairy" -> R.drawable.icon_fairy_type
        else ->  R.drawable.pokeball
    }
}