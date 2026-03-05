package com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model

import kotlin.time.Instant

data class WorkoutSession(
    val id: Long = 0,
    val completedAt: Instant,
    val durationSeconds: Int,
    val totalExercises: Int,
    val totalSets: Int,
    val planSnapshotJson: String
)