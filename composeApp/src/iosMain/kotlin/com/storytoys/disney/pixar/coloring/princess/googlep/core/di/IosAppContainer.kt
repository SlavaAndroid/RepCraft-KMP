package com.storytoys.disney.pixar.coloring.princess.googlep.core.di

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.di.AppContainer
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.WorkoutGenerator
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.WorkoutPlan
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.datastore.IosOnboardingStorage
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.datastore.OnboardingStorage
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.local.source.ExerciseDataSource
import com.storytoys.disney.pixar.coloring.princess.googlep.data.repository.IosUserProfileRepositoryImpl
import com.storytoys.disney.pixar.coloring.princess.googlep.data.repository.IosWorkoutRepositoryImpl
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.repository.UserProfileRepository
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.repository.WorkoutRepository

class IosAppContainer : AppContainer {
    override val onboardingStorage: OnboardingStorage = IosOnboardingStorage()
    override val exerciseDataSource = ExerciseDataSource()
    override val userProfileRepository: UserProfileRepository = IosUserProfileRepositoryImpl()
    override val workoutRepository: WorkoutRepository = IosWorkoutRepositoryImpl()
    override val workoutGenerator = WorkoutGenerator(exerciseDataSource)
    override var currentPlan: WorkoutPlan? = null
}
