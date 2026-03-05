package com.storytoys.disney.pixar.coloring.princess.googlep.feature.home.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.AppContainerHolder
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.UserProfile
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.WorkoutSession
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days

class HomeViewModel : ViewModel() {

    private val container = AppContainerHolder.instance
    private val userProfileRepo = container.userProfileRepository
    private val workoutRepo = container.workoutRepository

    val userProfile: StateFlow<UserProfile?> = userProfileRepo.observeUserProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val recentSessions: StateFlow<List<WorkoutSession>> = workoutRepo.observeAllSessions()
        .map { it.take(5) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSessions: StateFlow<List<WorkoutSession>> = workoutRepo.observeAllSessions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    var selectedDuration by mutableStateOf(30)
        private set

    fun setDuration(d: Int) { selectedDuration = d }

    fun generateWorkout(): Boolean {
        val profile = userProfile.value ?: return false
        val plan = container.workoutGenerator.generate(profile, selectedDuration)
        container.currentPlan = plan
        return true
    }

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

    fun totalWorkoutsThisWeek(sessions: List<WorkoutSession>): Int {
        val weekAgo = Clock.System.now() - 7.days
        return sessions.count { it.completedAt > weekAgo }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { HomeViewModel() }
        }
    }
}