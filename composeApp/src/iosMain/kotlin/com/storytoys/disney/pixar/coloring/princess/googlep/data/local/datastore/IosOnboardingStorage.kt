package com.storytoys.disney.pixar.coloring.princess.googlep.data.local.datastore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class IosOnboardingStorage : OnboardingStorage {
    private val _onboardingComplete = MutableStateFlow(false)

    override val onboardingComplete: Flow<Boolean> = _onboardingComplete

    override suspend fun setOnboardingComplete(complete: Boolean) {
        _onboardingComplete.value = complete
    }
}
