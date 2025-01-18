package com.eaapps.onboarding.domain.repository

interface OnBoardingRepository {

    suspend fun markOnBoardingAsShown()

    suspend fun isOnBoardingShown():Boolean

}