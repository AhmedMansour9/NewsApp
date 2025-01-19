package com.eaapps.newapp.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eaapps.onboarding.domain.usecases.IsOnBoardingShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val isOnBoardingShownUseCase: IsOnBoardingShownUseCase) : ViewModel() {

    private val _isOnBoardingShown = MutableStateFlow(false)
    val isOnBoardingShown = _isOnBoardingShown.asStateFlow()

    init {
        getOnBoardingStatus()
    }

    private fun getOnBoardingStatus() {
       viewModelScope.launch {
           _isOnBoardingShown.value = isOnBoardingShownUseCase()
       }
    }

}