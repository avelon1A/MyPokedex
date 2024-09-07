package com.example.mypokedex.di

import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.repo.PokemonRepositoryImp
import com.example.mypokedex.domain.repo.PokemonRepository
import com.example.mypokedex.domain.usecases.GetAppEntry
import com.example.mypokedex.domain.usecases.SaveAppEntry
import com.example.mypokedex.presentation.viewModels.HomeViewModel
import com.example.mypokedex.presentation.viewModels.PokemonPagingSource
import com.example.mypokedex.util.Const.BASE_URL
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { provideRetrofit() }
    single { providePokemonApi(get()) }
    single { providePokemonRepository(get()) }
    single { PokemonPagingSource(get()) }
    single { SaveAppEntry(get()) }
    single { GetAppEntry(get()) }
    viewModel { HomeViewModel(get(),get())}

}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun providePokemonApi(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}
fun providePokemonRepository(apiService: ApiService): PokemonRepository {
  return PokemonRepositoryImp(apiService)

}