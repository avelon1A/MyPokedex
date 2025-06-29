package com.example.mypokedex.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosch.composewithkotlin20.presentaion.ui.viewModel.event.OnBoardingEvent
import com.example.mypokedex.domain.usecases.AppEntryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class  SplashScreenViewModel( appEntryUseCase: AppEntryUseCase): ViewModel() {

    private val _hasAppEntry = MutableStateFlow(false)
    val hasAppEntry: StateFlow<Boolean> = _hasAppEntry

    init {
        viewModelScope.launch {
            appEntryUseCase.getAppEntry.invoke().collect { entry ->
                _hasAppEntry.value = entry
            }
        }
    }

}