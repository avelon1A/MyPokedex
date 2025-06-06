package com.example.mypokedex


import android.app.Application
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mypokedex.data.manager.LocalUserMangerImp
import com.example.mypokedex.data.manager.PokemonSyncWorker
import com.example.mypokedex.di.appModule
import com.example.mypokedex.util.PokemonWorkerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MyApp : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApp)
            modules(appModule)
        }

        checkAndRunWorker()
    }

    private fun checkAndRunWorker() {
        val userManager = LocalUserMangerImp(this)

        CoroutineScope(Dispatchers.IO).launch {
            userManager.isPokemonSyncDone().collect { hasEntered ->
                if (!hasEntered) {
                    WorkManager.getInstance(this@MyApp)
                        .enqueue(OneTimeWorkRequestBuilder<PokemonSyncWorker>().build())

                    userManager.setPokemonSyncDone()
                }
                cancel()
            }
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(PokemonWorkerFactory())
            .build()
}
