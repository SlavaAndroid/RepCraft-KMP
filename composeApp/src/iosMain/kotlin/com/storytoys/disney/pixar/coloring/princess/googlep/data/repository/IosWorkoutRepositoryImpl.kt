package com.storytoys.disney.pixar.coloring.princess.googlep.data.repository

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.WorkoutSession
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class IosWorkoutRepositoryImpl : WorkoutRepository {
    private val _sessions = MutableStateFlow<List<WorkoutSession>>(emptyList())
    private var nextId = 1L

    override fun observeAllSessions(): Flow<List<WorkoutSession>> = _sessions.asStateFlow()

    override suspend fun getRecentSessions(): List<WorkoutSession> = _sessions.value.takeLast(10)

    override suspend fun insertSession(session: WorkoutSession): Long {
        val id = nextId++
        _sessions.value = _sessions.value + session.copy(id = id)
        return id
    }

    override suspend fun getAllCompletedDates(): List<Long> =
        _sessions.value.map { it.completedAt.toEpochMilliseconds() }
}
