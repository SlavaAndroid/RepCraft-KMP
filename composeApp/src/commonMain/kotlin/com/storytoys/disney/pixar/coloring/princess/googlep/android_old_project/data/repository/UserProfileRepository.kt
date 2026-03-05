package com.storytoys.disney.pixar.coloring.princess.googlep.data.repository

import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun observeUserProfile(): Flow<UserProfile?>
    suspend fun saveUserProfile(profile: UserProfile)
}
