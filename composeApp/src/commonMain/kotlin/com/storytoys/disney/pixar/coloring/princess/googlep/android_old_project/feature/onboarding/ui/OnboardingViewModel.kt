package com.storytoys.disney.pixar.coloring.princess.googlep.feature.onboarding.ui

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
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.enums.Equipment
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.enums.ExperienceLevel
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.enums.Goal
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.enums.MuscleGroup
import kotlinx.coroutines.launch

class OnboardingViewModel : ViewModel() {

    private val repo = AppContainerHolder.instance.userProfileRepository

    var currentStep by mutableStateOf(0)
        private set

    var selectedEquipment by mutableStateOf(setOf<Equipment>())
        private set

    var selectedGoal by mutableStateOf(Goal.GENERAL_FITNESS)
        private set

    var selectedLevel by mutableStateOf(ExperienceLevel.BEGINNER)
        private set

    var weeklyFrequency by mutableStateOf(3)
        private set

    var selectedMuscles by mutableStateOf(setOf<MuscleGroup>())
        private set

    val totalSteps = 5

    fun toggleEquipment(e: Equipment) {
        selectedEquipment = if (e in selectedEquipment) selectedEquipment - e else selectedEquipment + e
    }

    fun setGoal(g: Goal) { selectedGoal = g }
    fun setLevel(l: ExperienceLevel) { selectedLevel = l }
    fun setFrequency(f: Int) { weeklyFrequency = f }

    fun toggleMuscle(m: MuscleGroup) {
        selectedMuscles = if (m in selectedMuscles) selectedMuscles - m else selectedMuscles + m
    }

    fun nextStep() { if (currentStep < totalSteps - 1) currentStep++ }
    fun prevStep() { if (currentStep > 0) currentStep-- }

    fun complete(onDone: () -> Unit) {
        viewModelScope.launch {
            val profile = UserProfile(
                equipment = selectedEquipment.toList(),
                goal = selectedGoal,
                experienceLevel = selectedLevel,
                weeklyFrequency = weeklyFrequency,
                targetMuscles = selectedMuscles.toList(),
                onboardingComplete = true
            )
            repo.saveUserProfile(profile)
            onDone()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { OnboardingViewModel() }
        }
    }
}