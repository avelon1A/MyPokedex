package com.example.mypokedex.data.localDataBase


import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon")
    fun getAllPokemon(): PagingSource<Int, PokemonEntity>

    @Query("DELETE FROM pokemon")
    suspend fun clearAll()
}

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}

