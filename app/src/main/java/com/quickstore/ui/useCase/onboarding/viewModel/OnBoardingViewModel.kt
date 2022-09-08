package com.quickstore.ui.useCase.onboarding.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.quickstore.data.onBoarding.model.OnBoardingModel
import com.quickstore.ui.useCase.onboarding.repository.OnBoardingRepository
import com.quickstore.util.repository.RepoResponse

class OnBoardingViewModel constructor(private val onBoardingRepository: OnBoardingRepository): ViewModel(){

    fun getSlider(): LiveData<RepoResponse<List<OnBoardingModel>>>{
        return onBoardingRepository.getSlider()
    }
}