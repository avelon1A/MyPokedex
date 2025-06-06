package com.example.mypokedex.data.manager
import android.content.Context
import android.util.Log
import androidx.room.withTransaction
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mypokedex.MyApp
import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.localDataBase.AppDatabase
import com.example.mypokedex.data.localDataBase.PokemonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonSyncWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    private val apiService: ApiService by lazy { (context.applicationContext as MyApp).apiService }
    private val database: AppDatabase by lazy { (context.applicationContext as MyApp).database }

    override suspend fun doWork(): Result {
        return try {
            val pageSize = 20
            var totalPokemon = 1000

            database.pokemonDao().clearAll();

            for (offset in 0 until totalPokemon step pageSize) {
                val response = apiService.getPokemonList(limit = pageSize, offset = offset)
                val pokemonList = response.results
                totalPokemon = response.count

                val pokemonEntities = withContext(Dispatchers.IO) {
                    pokemonList.map { pokemon ->
                        val details = apiService.getPokemonData(pokemon.name)
                        Log.d("worker","worker working $details")
                        PokemonEntity(
                            name = pokemon.name,
                            url = pokemon.url,
                            types = details.types.map { it.type.name }
                        )
                    }
                }

                database.withTransaction {
                    database.pokemonDao().insertAll(pokemonEntities)
                }
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}
