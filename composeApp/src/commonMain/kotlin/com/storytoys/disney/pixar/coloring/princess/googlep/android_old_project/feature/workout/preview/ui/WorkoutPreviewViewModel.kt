package com.storytoys.disney.pixar.coloring.princess.googlep.feature.workout.preview.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.AppContainerHolder
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.WorkoutPlan
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class WorkoutPreviewViewModel : ViewModel() {

    private val container = AppContainerHolder.instance

    var plan: WorkoutPlan? by mutableStateOf(container.currentPlan)
        private set

    fun regenerate() {
        viewModelScope.launch {
            val profile = container.userProfileRepository.observeUserProfile().firstOrNull() ?: return@launch
            val durationMinutes = container.currentPlan?.durationMinutes ?: 30
            val newPlan = container.workoutGenerator.generate(profile, durationMinutes)
            container.currentPlan = newPlan
            plan = newPlan
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { WorkoutPreviewViewModel() }
        }
    }
}