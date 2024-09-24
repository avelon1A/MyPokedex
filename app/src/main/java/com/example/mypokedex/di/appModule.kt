package com.example.mypokedex.di

import androidx.room.Room
import com.bosch.composewithkotlin20.data.manager.LocalUserMangerImp
import com.example.mypokedex.domain.usecases.LoginStatus
import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.localDataBase.AppDatabase
import com.example.mypokedex.data.localDataBase.PokemonDao
import com.example.mypokedex.data.repo.PokemonRepositoryImp
import com.example.mypokedex.domain.manger.LocalUserManager
import com.example.mypokedex.domain.repo.PokemonRepository
import com.example.mypokedex.domain.usecases.AppEntryUseCase
import com.example.mypokedex.domain.usecases.GetAppEntry
import com.example.mypokedex.domain.usecases.SaveAppEntry
import com.example.mypokedex.presentation.viewModels.HomeViewModel
import com.example.mypokedex.presentation.viewModels.OnBoardingViewModel
import com.example.mypokedex.presentation.viewModels.PokemonPagingSource
import com.example.mypokedex.util.Const.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { provideRetrofit() }
    single { providePokemonApi(get()) }
    single { providePokemonRepository(get(),get()) }
    single { PokemonPagingSource(get()) }
    single { SaveAppEntry(get()) }
    single { GetAppEntry(get()) }
    single { LoginStatus(get()) }
    single { AppEntryUseCase(get(), get()) }
    single<LocalUserManager> { LocalUserMangerImp(androidApplication()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "pokemon_database"
        ).build()
    }

    single {
        get<AppDatabase>().pokemonDao()
    }
    viewModel { HomeViewModel(get())}
    viewModel { OnBoardingViewModel(get()) }

}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}

fun providePokemonApi(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}
fun providePokemonRepository(apiService: ApiService,pokemonDao: PokemonDao): PokemonRepository {
  return PokemonRepositoryImp(apiService,pokemonDao)

}