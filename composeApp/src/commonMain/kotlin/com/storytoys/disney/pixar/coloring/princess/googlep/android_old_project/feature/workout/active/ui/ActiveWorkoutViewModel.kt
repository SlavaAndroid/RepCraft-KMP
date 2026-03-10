package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.workout.active.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.AppContainerHolder
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.PlannedExercise
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.WorkoutSession
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Clock

class ActiveWorkoutViewModel : ViewModel() {

    private val container = AppContainerHolder.instance
    private val workoutRepo = container.workoutRepository

    val plan = container.currentPlan
    val exercises: List<PlannedExercise> = plan?.allExercises ?: emptyList()

    var currentExerciseIndex by mutableStateOf(0)
        private set
    var currentSetNumber by mutableStateOf(1)
        private set
    var phase by mutableStateOf(WorkoutPhase.EXERCISING)
        private set
    var restTimeLeft by mutableStateOf(0)
        private set
    var exerciseTimeLeft by mutableStateOf(0)
        private set
    var elapsedSeconds by mutableStateOf(0)
        private set
    var isSaved by mutableStateOf(false)
        private set

    private var restJob: Job? = null
    private var exerciseJob: Job? = null

    enum class WorkoutPhase { EXERCISING, RESTING, COMPLETE }

    val currentExercise: PlannedExercise? get() = exercises.getOrNull(currentExerciseIndex)
    val totalExercises: Int get() = exercises.size
    val isTimedExercise: Boolean get() = currentExercise?.durationSeconds != null

    init {
        if (exercises.isNotEmpty()) {
            startGlobalTimer()
            initCurrentExercise()
        }
    }

    private fun startGlobalTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                elapsedSeconds++
            }
        }
    }

    private fun initCurrentExercise() {
        exerciseJob?.cancel()
        val ex = currentExercise ?: return
        if (ex.durationSeconds != null) {
            exerciseTimeLeft = ex.durationSeconds
            startExerciseCountdown()
        } else {
            exerciseTimeLeft = 0
        }
    }

    private fun startExerciseCountdown() {
        exerciseJob = viewModelScope.launch {
            while (exerciseTimeLeft > 0 && phase == WorkoutPhase.EXERCISING) {
                delay(1000)
                exerciseTimeLeft--
            }
            if (exerciseTimeLeft == 0 && phase == WorkoutPhase.EXERCISING) {
                onSetComplete()
            }
        }
    }

    fun onSetComplete() {
        exerciseJob?.cancel()
        val ex = currentExercise ?: return
        if (currentSetNumber < ex.sets) {
            phase = WorkoutPhase.RESTING
            restTimeLeft = ex.restAfterSeconds
            startRestTimer()
        } else {
            advanceToNextExercise()
        }
    }

    private fun startRestTimer() {
        restJob?.cancel()
        restJob = viewModelScope.launch {
            while (restTimeLeft > 0) {
                delay(1000)
                restTimeLeft--
            }
            if (phase == WorkoutPhase.RESTING) {
                currentSetNumber++
                phase = WorkoutPhase.EXERCISING
                initCurrentExercise()
            }
        }
    }

    fun skipRest() {
        restJob?.cancel()
        currentSetNumber++
        phase = WorkoutPhase.EXERCISING
        initCurrentExercise()
    }

    private fun advanceToNextExercise() {
        if (currentExerciseIndex < exercises.size - 1) {
            currentExerciseIndex++
            currentSetNumber = 1
            phase = WorkoutPhase.EXERCISING
            initCurrentExercise()
        } else {
            phase = WorkoutPhase.COMPLETE
        }
    }

    fun saveSession(onSaved: (Long) -> Unit) {
        if (isSaved) return
        isSaved = true
        viewModelScope.launch {
            val session = WorkoutSession(
                completedAt = Clock.System.now(),
                durationSeconds = elapsedSeconds,
                totalExercises = exercises.size,
                totalSets = exercises.sumOf { it.sets },
                planSnapshotJson = ""
            )
            val id = workoutRepo.insertSession(session)
            onSaved(id)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { ActiveWorkoutViewModel() }
        }
    }
}