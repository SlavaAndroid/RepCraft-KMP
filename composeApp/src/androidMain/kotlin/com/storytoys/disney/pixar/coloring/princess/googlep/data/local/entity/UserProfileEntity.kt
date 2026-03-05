package com.storytoys.disney.pixar.coloring.princess.googlep.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val equipmentJson: String,
    val goal: String,
    val experienceLevel: String,
    val weeklyFrequency: Int,
    val targetMusclesJson: String,
    val injuriesJson: String,
    val onboardingComplete: Boolean
)