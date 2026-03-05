package com.storytoys.disney.pixar.coloring.princess.googlep.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface OnboardingStorage {
    val onboardingComplete: Flow<Boolean>
    suspend fun setOnboardingComplete(complete: Boolean)
}