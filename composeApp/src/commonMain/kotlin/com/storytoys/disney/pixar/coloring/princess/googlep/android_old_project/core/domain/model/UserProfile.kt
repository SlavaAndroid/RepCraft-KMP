package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.Equipment
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.enums.ExperienceLevel
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.Goal
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.MuscleGroup

data class UserProfile(
    val equipment: List<Equipment> = emptyList(),
    val goal: Goal = Goal.GENERAL_FITNESS,
    val experienceLevel: ExperienceLevel = ExperienceLevel.BEGINNER,
    val weeklyFrequency: Int = 3,
    val targetMuscles: List<MuscleGroup> = emptyList(),
    val injuries: List<String> = emptyList(),
    val onboardingComplete: Boolean = false
)
