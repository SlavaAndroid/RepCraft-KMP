package com.storytoys.disney.pixar.coloring.princess.googlep.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "onboarding")

class OnboardingDataStore(private val context: Context) : OnboardingStorage {
    private val onboardingCompleteKey = booleanPreferencesKey("onboarding_complete")
    val userProfileJsonKey = stringPreferencesKey("user_profile_json")

    override val onboardingComplete: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[onboardingCompleteKey] ?: false }

    val userProfileJson: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[userProfileJsonKey] }

    override suspend fun setOnboardingComplete(complete: Boolean) {
        context.dataStore.edit { prefs -> prefs[onboardingCompleteKey] = complete }
    }

    suspend fun setUserProfileJson(json: String) {
        context.dataStore.edit { prefs -> prefs[userProfileJsonKey] = json }
    }
}