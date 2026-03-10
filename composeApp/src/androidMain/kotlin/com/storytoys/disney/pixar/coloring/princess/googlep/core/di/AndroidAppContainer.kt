package com.storytoys.disney.pixar.coloring.princess.googlep.core.di

import android.content.Context
import androidx.room.Room
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.di.AppContainer
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.WorkoutGenerator
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.WorkoutPlan
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.AppDatabase
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.datastore.OnboardingDataStore
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.datastore.OnboardingStorage
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.local.source.ExerciseDataSource
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.repository.UserProfileRepository
import com.storytoys.disney.pixar.coloring.princess.googlep.data.repository.UserProfileRepositoryImpl
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.repository.WorkoutRepository
import com.storytoys.disney.pixar.coloring.princess.googlep.data.repository.WorkoutRepositoryImpl

class AndroidAppContainer(context: Context) : AppContainer {

    private val db: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "repcraft.db"
    ).build()

    private val onboardingDataStore = OnboardingDataStore(context.applicationContext)

    override val onboardingStorage: OnboardingStorage = onboardingDataStore
    override val exerciseDataSource = ExerciseDataSource()
    override val userProfileRepository: UserProfileRepository =
        UserProfileRepositoryImpl(db.userProfileDao(), onboardingDataStore)
    override val workoutRepository: WorkoutRepository =
        WorkoutRepositoryImpl(db.workoutSessionDao())
    override val workoutGenerator = WorkoutGenerator(exerciseDataSource)
    override var currentPlan: WorkoutPlan? = null
}
