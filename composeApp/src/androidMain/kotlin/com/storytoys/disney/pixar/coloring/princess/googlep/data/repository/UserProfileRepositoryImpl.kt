package com.storytoys.disney.pixar.coloring.princess.googlep.data.repository

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.UserProfile
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.Equipment
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.enums.ExperienceLevel
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.Goal
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.MuscleGroup
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.repository.UserProfileRepository
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.dao.UserProfileDao
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.datastore.OnboardingDataStore
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray

class UserProfileRepositoryImpl(
    private val dao: UserProfileDao,
    private val dataStore: OnboardingDataStore
) : UserProfileRepository {

    override fun observeUserProfile(): Flow<UserProfile?> =
        dao.observe().map { entity -> entity?.toDomain() }

    override suspend fun saveUserProfile(profile: UserProfile) {
        dao.upsert(profile.toEntity())
        dataStore.setOnboardingComplete(profile.onboardingComplete)
    }

    private fun UserProfileEntity.toDomain() = UserProfile(
        equipment = parseJsonArray(equipmentJson).map { Equipment.valueOf(it) },
        goal = Goal.valueOf(goal),
        experienceLevel = ExperienceLevel.valueOf(experienceLevel),
        weeklyFrequency = weeklyFrequency,
        targetMuscles = parseJsonArray(targetMusclesJson).map { MuscleGroup.valueOf(it) },
        injuries = parseJsonArray(injuriesJson),
        onboardingComplete = onboardingComplete
    )

    private fun UserProfile.toEntity() = UserProfileEntity(
        id = 1,
        equipmentJson = toJsonArray(equipment.map { it.name }),
        goal = goal.name,
        experienceLevel = experienceLevel.name,
        weeklyFrequency = weeklyFrequency,
        targetMusclesJson = toJsonArray(targetMuscles.map { it.name }),
        injuriesJson = toJsonArray(injuries),
        onboardingComplete = onboardingComplete
    )

    private fun toJsonArray(list: List<String>): String {
        val arr = JSONArray()
        list.forEach { arr.put(it) }
        return arr.toString()
    }

    private fun parseJsonArray(json: String): List<String> {
        val arr = JSONArray(json)
        return (0 until arr.length()).map { arr.getString(it) }
    }
}