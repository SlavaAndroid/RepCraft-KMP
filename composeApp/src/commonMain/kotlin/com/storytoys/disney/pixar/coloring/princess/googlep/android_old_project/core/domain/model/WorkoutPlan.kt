package com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model

import kotlin.time.Clock
import kotlin.time.Instant

data class WorkoutPlan(
    val generatedAt: Instant = Clock.System.now(),
    val durationMinutes: Int,
    val warmUp: List<PlannedExercise>,
    val main: List<PlannedExercise>,
    val cooldown: List<PlannedExercise>
) {
    val allExercises: List<PlannedExercise>
        get() = warmUp + main + cooldown
}