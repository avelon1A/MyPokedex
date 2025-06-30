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
    suspend fun insertAll(pokemon: List<PokemonEntity>)

    @Query("SELECT * FROM pokemonDB")
    fun getAllPokemon(): PagingSource<Int, PokemonEntity>

    @Query("DELETE FROM pokemonDB")
    suspend fun clearAll()

@Query("SELECT * FROM pokemonDB WHERE name LIKE '%' || :name || '%'")
suspend fun getPokemonByName(name: String): List<PokemonEntity>


    @Query("UPDATE pokemonDB SET favorite = :isFavorite WHERE name = :pokemonName")
    suspend fun updatePokemonFav(isFavorite: Int, pokemonName: String)
}

@Database(entities = [PokemonEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}

