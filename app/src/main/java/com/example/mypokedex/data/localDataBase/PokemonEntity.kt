package com.example.mypokedex.data.localDataBase


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "pokemonDB")
@TypeConverters(Converters::class)
data class PokemonEntity(
    @PrimaryKey val name: String,
    val url: String,
    val types: List<String>
)
