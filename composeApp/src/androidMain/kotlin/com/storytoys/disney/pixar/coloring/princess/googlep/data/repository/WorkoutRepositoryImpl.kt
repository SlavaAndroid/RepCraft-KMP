package com.storytoys.disney.pixar.coloring.princess.googlep.data.repository

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.WorkoutSession
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.repository.WorkoutRepository
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.dao.WorkoutSessionDao
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.entity.WorkoutSessionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Instant

class WorkoutRepositoryImpl(
    private val dao: WorkoutSessionDao
) : WorkoutRepository {

    override fun observeAllSessions(): Flow<List<WorkoutSession>> =
        dao.observeAll().map { list ->
            println("WorkoutRepository: Loaded ${list.size} sessions from database")
            list.map { it.toDomain() }
        }

    override suspend fun getRecentSessions(): List<WorkoutSession> =
        dao.getRecent().map { it.toDomain() }

    override suspend fun insertSession(session: WorkoutSession): Long {
        return try {
            val id = dao.insert(session.toEntity())
            println("WorkoutRepository: Saved session id=$id completedAt=${session.completedAt}")
            id
        } catch (e: Exception) {
            println("WorkoutRepository: Error saving session: ${e.message}")
            throw e
        }
    }

    override suspend fun getAllCompletedDates(): List<Long> =
        dao.getAllCompletedDates()

    private fun WorkoutSessionEntity.toDomain() = WorkoutSession(
        id = id,
        completedAt = Instant.fromEpochMilliseconds(completedAt),
        durationSeconds = durationSeconds,
        totalExercises = totalExercises,
        totalSets = totalSets,
        planSnapshotJson = planSnapshotJson
    )

    private fun WorkoutSession.toEntity() = WorkoutSessionEntity(
        id = id,
        completedAt = completedAt.toEpochMilliseconds(),
        durationSeconds = durationSeconds,
        totalExercises = totalExercises,
        totalSets = totalSets,
        planSnapshotJson = planSnapshotJson
    )
}