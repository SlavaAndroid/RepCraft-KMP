package com.storytoys.disney.pixar.coloring.princess.googlep.feature.workout.summary.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.AppContainerHolder
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.WorkoutSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WorkoutSummaryViewModel : ViewModel() {

    private val workoutRepo = AppContainerHolder.instance.workoutRepository

    private val _session = MutableStateFlow<WorkoutSession?>(null)
    val session: StateFlow<WorkoutSession?> = _session

    fun loadSession(sessionId: Long) {
        viewModelScope.launch {
            val sessions = workoutRepo.observeAllSessions().first()
            _session.value = sessions.find { it.id == sessionId }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { WorkoutSummaryViewModel() }
        }
    }
}