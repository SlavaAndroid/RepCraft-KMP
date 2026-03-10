package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.Equipment
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.enums.ExperienceLevel
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.MuscleGroup
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.WorkoutPhase

data class Exercise(
    val id: String,
    val name: String,
    val description: String,
    val muscleGroups: List<MuscleGroup>,
    val requiredEquipment: List<Equipment>,
    val experienceLevels: List<ExperienceLevel>,
    val phase: WorkoutPhase,
    val durationSeconds: Int? = null,
    val defaultSets: Int,
    val defaultReps: Int? = null,
    val instructionSteps: List<String>
)
