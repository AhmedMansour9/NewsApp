package com.eaapps.onboarding.domain.usecases

 import com.eaapps.onboarding.domain.repository.OnBoardingRepository
import javax.inject.Inject

class MarkOnBoardingAsShownUseCase @Inject constructor(private val repository: OnBoardingRepository) {
    suspend operator fun invoke() = repository.markOnBoardingAsShown()
}