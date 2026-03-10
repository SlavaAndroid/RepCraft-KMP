package com.storytoys.disney.pixar.coloring.princess.googlep.data.repository

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.UserProfile
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class IosUserProfileRepositoryImpl : UserProfileRepository {
    private val _profile = MutableStateFlow<UserProfile?>(null)

    override fun observeUserProfile(): Flow<UserProfile?> = _profile

    override suspend fun saveUserProfile(profile: UserProfile) {
        _profile.value = profile
    }
}
