package com.eaapps.onboarding.data.datasources

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val ONBOARDING_KEY = "onboarding"

interface OnBoardingCacheDatasource {
    suspend fun markOnBoardingAsShown()
    suspend fun isOnBoardingShown(): Boolean
}

class OnBoardingCacheDatasourceImpl @Inject constructor(private val shared: SharedPreferences) : OnBoardingCacheDatasource {
    override suspend fun markOnBoardingAsShown() = withContext(Dispatchers.IO) {
        shared.edit().putBoolean(ONBOARDING_KEY, true).apply()
    }

    override suspend fun isOnBoardingShown(): Boolean = withContext(Dispatchers.IO) {
        shared.getBoolean(ONBOARDING_KEY, false)
    }


}