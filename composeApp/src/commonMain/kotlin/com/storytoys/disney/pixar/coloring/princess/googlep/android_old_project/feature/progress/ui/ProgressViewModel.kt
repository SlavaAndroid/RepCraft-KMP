package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.progress.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.AppContainerHolder
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.WorkoutSession
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days

class ProgressViewModel : ViewModel() {

    private val workoutRepo = AppContainerHolder.instance.workoutRepository

    val sessions: StateFlow<List<WorkoutSession>> = workoutRepo.observeAllSessions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun calculateStreak(sessions: List<WorkoutSession>): Int {
        if (sessions.isEmpty()) return 0
        val zone = TimeZone.currentSystemDefault()
        val dates = sessions
            .map { it.completedAt.toLocalDateTime(zone).date }
            .distinct()
            .sortedDescending()
        var streak = 0
        var expected = Clock.System.now().toLocalDateTime(zone).date
        for (date in dates) {
            if (date == expected || date == expected.minus(DatePeriod(days = 1))) {
                streak++
                expected = date.minus(DatePeriod(days = 1))
            } else break
        }
        return streak
    }

    fun totalThisWeek(sessions: List<WorkoutSession>): Int {
        val weekAgo = Clock.System.now() - 7.days
        return sessions.count { it.completedAt > weekAgo }
    }

    fun totalMinutes(sessions: List<WorkoutSession>): Int =
        sessions.sumOf { it.durationSeconds } / 60

    fun avgDuration(sessions: List<WorkoutSession>): Int =
        if (sessions.isEmpty()) 0 else sessions.sumOf { it.durationSeconds } / sessions.size / 60

    fun weeklyData(sessions: List<WorkoutSession>): List<Pair<String, Int>> {
        val zone = TimeZone.currentSystemDefault()
        val dayLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        val today = Clock.System.now().toLocalDateTime(zone).date
        // DayOfWeek ordinal: MONDAY=0 ... SUNDAY=6
        val daysFromMonday = today.dayOfWeek.ordinal
        val mondayOfWeek = today.minus(DatePeriod(days = daysFromMonday))
        return dayLabels.mapIndexed { i, label ->
            val day = mondayOfWeek.plus(DatePeriod(days = i))
            val count = sessions.count { it.completedAt.toLocalDateTime(zone).date == day }
            label to count
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { ProgressViewModel() }
        }
    }
}