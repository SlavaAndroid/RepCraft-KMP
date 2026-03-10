package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.library.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.AppContainerHolder
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.Exercise
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.Equipment
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.MuscleGroup

class ExerciseLibraryViewModel : ViewModel() {

    private val dataSource = AppContainerHolder.instance.exerciseDataSource

    val allExercises: List<Exercise> = dataSource.mainExercises

    var searchQuery by mutableStateOf("")
        private set

    var selectedMuscle by mutableStateOf<MuscleGroup?>(null)
        private set

    var selectedEquipment by mutableStateOf<Equipment?>(null)
        private set

    var selectedExercise by mutableStateOf<Exercise?>(null)
        private set

    fun setSearch(q: String) { searchQuery = q }
    fun setMuscleFilter(m: MuscleGroup?) { selectedMuscle = m }
    fun setEquipmentFilter(e: Equipment?) { selectedEquipment = e }
    fun selectExercise(e: Exercise?) { selectedExercise = e }

    val filteredExercises: List<Exercise>
        get() = allExercises.filter { ex ->
            (searchQuery.isEmpty() || ex.name.contains(searchQuery, ignoreCase = true) || ex.description.contains(searchQuery, ignoreCase = true)) &&
            (selectedMuscle == null || ex.muscleGroups.contains(selectedMuscle)) &&
            (selectedEquipment == null || ex.requiredEquipment.contains(selectedEquipment))
        }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { ExerciseLibraryViewModel() }
        }
    }
}