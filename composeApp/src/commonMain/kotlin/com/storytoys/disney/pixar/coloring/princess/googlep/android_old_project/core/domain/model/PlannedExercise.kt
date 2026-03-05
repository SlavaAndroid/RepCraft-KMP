package com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model

data class PlannedExercise(
    val exercise: Exercise,
    val sets: Int,
    val reps: Int? = null,
    val durationSeconds: Int? = null,
    val restAfterSeconds: Int
)
