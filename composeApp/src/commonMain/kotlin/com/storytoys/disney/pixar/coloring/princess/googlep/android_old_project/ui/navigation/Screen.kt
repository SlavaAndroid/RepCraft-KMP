package com.storytoys.disney.pixar.coloring.princess.googlep.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object WorkoutPreview : Screen("workout_preview")
    object ActiveWorkout : Screen("active_workout")
    data class WorkoutSummary(val sessionId: Long = 0) : Screen("workout_summary/{sessionId}") {
        companion object {
            const val ROUTE = "workout_summary/{sessionId}"
            fun createRoute(sessionId: Long) = "workout_summary/$sessionId"
        }
    }
    object ExerciseLibrary : Screen("exercise_library")
    data class ExerciseDetail(val exerciseId: String = "") : Screen("exercise_detail/{exerciseId}") {
        companion object {
            const val ROUTE = "exercise_detail/{exerciseId}"
            fun createRoute(exerciseId: String) = "exercise_detail/$exerciseId"
        }
    }
    object Progress : Screen("progress")
    object WorkoutHistory : Screen("workout_history")
}
