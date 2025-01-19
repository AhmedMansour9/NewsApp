package com.eaapps.onboarding.data.repository

import com.eaapps.onboarding.data.datasources.OnBoardingCacheDatasource
import com.eaapps.onboarding.domain.repository.OnBoardingRepository
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(private val cache: OnBoardingCacheDatasource) : OnBoardingRepository {
    override suspend fun markOnBoardingAsShown() = cache.markOnBoardingAsShown()

    override suspend fun isOnBoardingShown(): Boolean = cache.isOnBoardingShown()
}