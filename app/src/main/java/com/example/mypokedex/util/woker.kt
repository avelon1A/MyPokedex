package com.example.mypokedex.util

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.localDataBase.AppDatabase
import com.example.mypokedex.data.manager.PokemonSyncWorker

class PokemonWorkerFactory(
    private val apiService: ApiService,
    private val database: AppDatabase
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParams: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            PokemonSyncWorker::class.java.name -> PokemonSyncWorker(appContext, workerParams)
            else -> null
        }
    }
}
