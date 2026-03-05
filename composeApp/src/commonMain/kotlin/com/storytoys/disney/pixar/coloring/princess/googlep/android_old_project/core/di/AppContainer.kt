package com.storytoys.disney.pixar.coloring.princess.googlep.core.di

import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.WorkoutGenerator
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.WorkoutPlan
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.datastore.OnboardingStorage
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.source.ExerciseDataSource
import com.storytoys.disney.pixar.coloring.princess.googlep.data.repository.UserProfileRepository
import com.storytoys.disney.pixar.coloring.princess.googlep.data.repository.WorkoutRepository

interface AppContainer {
    val onboardingStorage: OnboardingStorage
    val exerciseDataSource: ExerciseDataSource
    val userProfileRepository: UserProfileRepository
    val workoutRepository: WorkoutRepository
    val workoutGenerator: WorkoutGenerator
    var currentPlan: WorkoutPlan?
}