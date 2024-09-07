package com.example.mypokedex.data.model

import androidx.annotation.DrawableRes
import com.example.mypokedex.R

data class Page(
	val title:String,
	val content:String,
	@DrawableRes val image:Int
)
val pages = listOf(
	Page(
		"All pokemon in one place",
		"Access a vast list of Pokémon from \n" +
				"all generations ever made by Nintendo",
		R.drawable.on_boarding_one
	),
	Page(
		"Keep your \n" +
				"Updated Pokédex",
		"Register and maintain your profile, \n" +
				"Favorite Pokémon, settings and much more, saved in the application, even without an internet connection.",
		R.drawable.on_boarding_two
	),
	Page(
		"Are you ready for this adventure?",
		"Simply create an account and start exploring the world of Pokémon today!",
		R.drawable.on_boarding_login
	),
	)