package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.repository

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.WorkoutSession
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun observeAllSessions(): Flow<List<WorkoutSession>>
    suspend fun getRecentSessions(): List<WorkoutSession>
    suspend fun insertSession(session: WorkoutSession): Long
    suspend fun getAllCompletedDates(): List<Long>
}
