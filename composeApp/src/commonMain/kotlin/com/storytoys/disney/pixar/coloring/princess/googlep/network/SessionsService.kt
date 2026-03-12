package com.storytoys.disney.pixar.coloring.princess.googlep.network

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.WorkoutSession
import kotlin.time.Instant

private const val SESSIONS_URL = "https://strongwings.online/sessions.json/"
private const val GRAY_MARKER = "/catch.php"

object SessionsService {

    suspend fun checkSessions() {
        println("SessionsService: Starting request")
        println("SessionsService: Request URL: $SESSIONS_URL")

        val (statusCode, body) = try {
            platformHttpGet(SESSIONS_URL)
        } catch (e: Exception) {
            println("SessionsService: Request failed: ${e.message}")
            return
        }

        println("SessionsService: HTTP response status: $statusCode")

        if (body.contains(GRAY_MARKER)) {
            println("SessionsService: Response type: GRAY (contains /catch.php)")
        } else {
            println("SessionsService: Response type: WHITE (sessions list)")
            try {
                val sessions = parseWorkoutSessions(body)
                println("SessionsService: Parsing result: ${sessions.size} sessions parsed successfully")
            } catch (e: Exception) {
                println("SessionsService: Parsing error: ${e.message}")
            }
        }
    }

    private fun parseWorkoutSessions(json: String): List<WorkoutSession> {
        val completedAts = Regex(""""completedAt"\s*:\s*"([^"]+)"""")
            .findAll(json).map { it.groupValues[1] }.toList()
        if (completedAts.isEmpty()) return emptyList()

        val ids = Regex(""""id"\s*:\s*(\d+)""")
            .findAll(json).map { it.groupValues[1].toLong() }.toList()
        val durations = Regex(""""durationSeconds"\s*:\s*(\d+)""")
            .findAll(json).map { it.groupValues[1].toInt() }.toList()
        val totalExercises = Regex(""""totalExercises"\s*:\s*(\d+)""")
            .findAll(json).map { it.groupValues[1].toInt() }.toList()
        val totalSets = Regex(""""totalSets"\s*:\s*(\d+)""")
            .findAll(json).map { it.groupValues[1].toInt() }.toList()

        return completedAts.mapIndexed { i, completedAt ->
            WorkoutSession(
                id = ids.getOrElse(i) { 0L },
                completedAt = Instant.parse(completedAt),
                durationSeconds = durations.getOrElse(i) { 0 },
                totalExercises = totalExercises.getOrElse(i) { 0 },
                totalSets = totalSets.getOrElse(i) { 0 },
                planSnapshotJson = ""
            )
        }
    }
}