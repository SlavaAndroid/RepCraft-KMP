package com.storytoys.disney.pixar.coloring.princess.googlep.data.repository

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.UserProfile
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.repository.UserProfileRepository
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.datastore.OnboardingStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class IosUserProfileRepositoryImpl(
    private val onboardingStorage: OnboardingStorage
) : UserProfileRepository {
    private val _profile = MutableStateFlow<UserProfile?>(null)

    override fun observeUserProfile(): Flow<UserProfile?> = _profile

    override suspend fun saveUserProfile(profile: UserProfile) {
        _profile.value = profile
        onboardingStorage.setOnboardingComplete(profile.onboardingComplete)
    }
}
