package com.storytoys.disney.pixar.coloring.princess.googlep.data.repository

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.WorkoutSession
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.Foundation.NSUserDefaults
import kotlin.time.Instant

private const val KEY_SESSIONS = "workout_sessions_v1"

class IosWorkoutRepositoryImpl : WorkoutRepository {

    private val userDefaults = NSUserDefaults.standardUserDefaults

    private val _sessions = MutableStateFlow<List<WorkoutSession>>(emptyList())

    init {
        println("WorkoutRepository: Loading sessions from NSUserDefaults")
        val loaded = loadSessions()
        _sessions.value = loaded
        println("WorkoutRepository: Loaded ${loaded.size} sessions")
    }

    override fun observeAllSessions(): Flow<List<WorkoutSession>> = _sessions.asStateFlow()

    override suspend fun getRecentSessions(): List<WorkoutSession> = _sessions.value.takeLast(10)

    override suspend fun insertSession(session: WorkoutSession): Long {
        val id = (_sessions.value.maxOfOrNull { it.id } ?: 0L) + 1L
        val newSession = session.copy(id = id)
        val updated = _sessions.value + newSession
        _sessions.value = updated
        try {
            saveSessions(updated)
            println("WorkoutRepository: Saved session id=$id, total=${updated.size}")
        } catch (e: Exception) {
            println("WorkoutRepository: Error saving session: ${e.message}")
        }
        return id
    }

    override suspend fun getAllCompletedDates(): List<Long> =
        _sessions.value.map { it.completedAt.toEpochMilliseconds() }

    private fun saveSessions(sessions: List<WorkoutSession>) {
        val json = buildString {
            append("[")
            sessions.forEachIndexed { index, s ->
                if (index > 0) append(",")
                append("{")
                append("\"id\":${s.id},")
                append("\"completedAt\":${s.completedAt.toEpochMilliseconds()},")
                append("\"durationSeconds\":${s.durationSeconds},")
                append("\"totalExercises\":${s.totalExercises},")
                append("\"totalSets\":${s.totalSets}")
                append("}")
            }
            append("]")
        }
        userDefaults.setObject(json, forKey = KEY_SESSIONS)
    }

    private fun loadSessions(): List<WorkoutSession> {
        val json = userDefaults.stringForKey(KEY_SESSIONS) ?: return emptyList()
        return try {
            parseSessionsJson(json)
        } catch (e: Exception) {
            println("WorkoutRepository: Error parsing saved sessions: ${e.message}")
            emptyList()
        }
    }

    private fun parseSessionsJson(json: String): List<WorkoutSession> {
        val ids = Regex(""""id"\s*:\s*(\d+)""")
            .findAll(json).map { it.groupValues[1].toLong() }.toList()
        val completedAts = Regex(""""completedAt"\s*:\s*(\d+)""")
            .findAll(json).map { it.groupValues[1].toLong() }.toList()
        val durations = Regex(""""durationSeconds"\s*:\s*(\d+)""")
            .findAll(json).map { it.groupValues[1].toInt() }.toList()
        val totalExercises = Regex(""""totalExercises"\s*:\s*(\d+)""")
            .findAll(json).map { it.groupValues[1].toInt() }.toList()
        val totalSets = Regex(""""totalSets"\s*:\s*(\d+)""")
            .findAll(json).map { it.groupValues[1].toInt() }.toList()

        return ids.mapIndexed { i, id ->
            WorkoutSession(
                id = id,
                completedAt = Instant.fromEpochMilliseconds(completedAts.getOrElse(i) { 0L }),
                durationSeconds = durations.getOrElse(i) { 0 },
                totalExercises = totalExercises.getOrElse(i) { 0 },
                totalSets = totalSets.getOrElse(i) { 0 },
                planSnapshotJson = ""
            )
        }
    }
}