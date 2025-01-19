package com.eaapps.newapp.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eaapps.onboarding.domain.usecases.IsOnBoardingShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val isOnBoardingShownUseCase: IsOnBoardingShownUseCase) : ViewModel() {

    private val _isOnBoardingShown = MutableSharedFlow<Boolean>(1)
    val isOnBoardingShown = _isOnBoardingShown.asSharedFlow()

    init {
        getOnBoardingStatus()
    }

    private fun getOnBoardingStatus() {
       viewModelScope.launch {
           _isOnBoardingShown.tryEmit(isOnBoardingShownUseCase())
       }
    }

}