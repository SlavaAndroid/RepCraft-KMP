package com.storytoys.disney.pixar.coloring.princess.googlep.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_session")
data class WorkoutSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val completedAt: Long,
    val durationSeconds: Int,
    val totalExercises: Int,
    val totalSets: Int,
    val planSnapshotJson: String
)