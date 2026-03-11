package com.storytoys.disney.pixar.coloring.princess.googlep.data.local.datastore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import platform.Foundation.NSUserDefaults

private const val KEY_ONBOARDING_COMPLETE = "onboarding_complete"

class IosOnboardingStorage : OnboardingStorage {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    private val _onboardingComplete = MutableStateFlow(
        userDefaults.boolForKey(KEY_ONBOARDING_COMPLETE)
    )

    override val onboardingComplete: Flow<Boolean> = _onboardingComplete

    override suspend fun setOnboardingComplete(complete: Boolean) {
        userDefaults.setBool(complete, forKey = KEY_ONBOARDING_COMPLETE)
        _onboardingComplete.value = complete
    }
}
