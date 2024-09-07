package com.bosch.composewithkotlin20.presentaion.ui.viewModel.event

sealed class OnBoardingEvent {
	data object SaveAtEntryPoint: OnBoardingEvent()
}