package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.di

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.WorkoutGenerator
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.WorkoutPlan
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.datastore.OnboardingStorage
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.local.source.ExerciseDataSource
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.repository.UserProfileRepository
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.repository.WorkoutRepository

interface AppContainer {
    val onboardingStorage: OnboardingStorage
    val exerciseDataSource: ExerciseDataSource
    val userProfileRepository: UserProfileRepository
    val workoutRepository: WorkoutRepository
    val workoutGenerator: WorkoutGenerator
    var currentPlan: WorkoutPlan?
}