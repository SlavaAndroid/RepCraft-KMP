package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.Exercise
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.PlannedExercise
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.UserProfile
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.WorkoutPlan
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.Equipment
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.Goal
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.MuscleGroup
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.data.local.source.ExerciseDataSource

class WorkoutGenerator(private val dataSource: ExerciseDataSource) {

    fun generate(profile: UserProfile, durationMinutes: Int): WorkoutPlan {
        val warmUp = selectWarmUp()
        val cooldown = selectCooldown(profile)
        val main = selectMain(profile, durationMinutes)
        return WorkoutPlan(
            durationMinutes = durationMinutes,
            warmUp = warmUp,
            main = main,
            cooldown = cooldown
        )
    }

    private fun selectWarmUp(): List<PlannedExercise> =
        dataSource.warmUpExercises
            .shuffled()
            .take(3)
            .map { ex ->
                PlannedExercise(
                    exercise = ex,
                    sets = 1,
                    durationSeconds = ex.durationSeconds ?: 30,
                    restAfterSeconds = 10
                )
            }

    private fun selectCooldown(profile: UserProfile): List<PlannedExercise> =
        dataSource.cooldownExercises
            .filter { ex -> canDoExercise(ex, profile) }
            .shuffled()
            .take(3)
            .map { ex ->
                PlannedExercise(
                    exercise = ex,
                    sets = 1,
                    durationSeconds = ex.durationSeconds ?: 30,
                    restAfterSeconds = 10
                )
            }

    private fun selectMain(profile: UserProfile, totalMinutes: Int): List<PlannedExercise> {
        val mainMinutes = (totalMinutes - 10).coerceAtLeast(5)
        val available = dataSource.mainExercises.filter { ex ->
            canDoExercise(ex, profile) && profile.experienceLevel in ex.experienceLevels
        }

        val (sets, restSeconds) = when (profile.goal) {
            Goal.BUILD_MUSCLE -> 4 to 90
            Goal.LOSE_WEIGHT -> 3 to 45
            Goal.IMPROVE_ENDURANCE -> 3 to 30
            Goal.INCREASE_FLEXIBILITY -> 2 to 20
            Goal.GENERAL_FITNESS -> 3 to 60
        }

        val repsForGoal = when (profile.goal) {
            Goal.BUILD_MUSCLE -> 10
            Goal.LOSE_WEIGHT -> 15
            Goal.IMPROVE_ENDURANCE -> 20
            Goal.INCREASE_FLEXIBILITY -> 12
            Goal.GENERAL_FITNESS -> 12
        }

        val balanced = balanceByMuscleGroups(available, profile)
        val timePerExercise = sets * (40 + restSeconds)
        val maxExercises = ((mainMinutes * 60) / timePerExercise).coerceIn(3, 10)

        return balanced.take(maxExercises).map { ex ->
            PlannedExercise(
                exercise = ex,
                sets = sets,
                reps = if (ex.durationSeconds == null) repsForGoal else null,
                durationSeconds = ex.durationSeconds,
                restAfterSeconds = restSeconds
            )
        }
    }

    private fun canDoExercise(ex: Exercise, profile: UserProfile): Boolean =
        ex.requiredEquipment.any { it == Equipment.NO_EQUIPMENT || it in profile.equipment }

    private fun balanceByMuscleGroups(available: List<Exercise>, profile: UserProfile): List<Exercise> {
        val push = available.filter { ex ->
            ex.muscleGroups.any { it in listOf(MuscleGroup.CHEST, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS) }
        }
        val pull = available.filter { ex ->
            ex.muscleGroups.any { it in listOf(MuscleGroup.BACK, MuscleGroup.BICEPS) }
        }
        val legs = available.filter { ex ->
            ex.muscleGroups.any { it in listOf(MuscleGroup.QUADS, MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES, MuscleGroup.CALVES) }
        }
        val core = available.filter { ex -> ex.muscleGroups.any { it == MuscleGroup.CORE } }
        val full = available.filter { ex -> ex.muscleGroups.any { it == MuscleGroup.FULL_BODY } }

        val result = mutableListOf<Exercise>()
        val seen = mutableSetOf<String>()

        fun addFrom(list: List<Exercise>, n: Int) {
            list.shuffled().filter { it.id !in seen }.take(n).forEach {
                result.add(it); seen.add(it.id)
            }
        }

        val upperPriority = listOf(MuscleGroup.CHEST, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS)
        val pullPriority = listOf(MuscleGroup.BACK, MuscleGroup.BICEPS)
        val legPriority = listOf(MuscleGroup.QUADS, MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES, MuscleGroup.CALVES)

        if (profile.targetMuscles.isNotEmpty()) {
            when {
                profile.targetMuscles.any { it in upperPriority } -> {
                    addFrom(push, 3); addFrom(pull, 2); addFrom(legs, 2); addFrom(core, 1)
                }
                profile.targetMuscles.any { it in pullPriority } -> {
                    addFrom(pull, 3); addFrom(push, 2); addFrom(legs, 2); addFrom(core, 1)
                }
                profile.targetMuscles.any { it in legPriority } -> {
                    addFrom(legs, 4); addFrom(core, 2); addFrom(push, 1); addFrom(pull, 1)
                }
                profile.targetMuscles.contains(MuscleGroup.CORE) -> {
                    addFrom(core, 4); addFrom(full, 2); addFrom(push, 1); addFrom(pull, 1)
                }
                else -> {
                    addFrom(push, 2); addFrom(pull, 2); addFrom(legs, 2); addFrom(core, 2); addFrom(full, 2)
                }
            }
        } else {
            addFrom(push, 2); addFrom(pull, 2); addFrom(legs, 2); addFrom(core, 2); addFrom(full, 2)
        }
        addFrom(available, available.size)
        return result
    }
}
