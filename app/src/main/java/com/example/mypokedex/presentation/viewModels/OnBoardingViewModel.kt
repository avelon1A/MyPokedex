package com.example.mypokedex.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosch.composewithkotlin20.presentaion.ui.viewModel.event.OnBoardingEvent
import com.example.mypokedex.domain.usecases.AppEntryUseCase

import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val appEntryUseCase: AppEntryUseCase
) : ViewModel() {
    fun OnEvent(event: OnBoardingEvent) {
        when(event){
            is OnBoardingEvent.SaveAtEntryPoint  -> {
                saveAppEntry()
            }
        }
    }

    private fun saveAppEntry() {
        viewModelScope.launch {
            appEntryUseCase.saveAppEntry()
        }
    }
}