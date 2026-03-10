package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.home.ui.HomeScreen
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.library.ui.ExerciseLibraryScreen
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.onboarding.ui.OnboardingScreen
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.progress.ui.ProgressDashboardScreen
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.progress.ui.WorkoutHistoryScreen
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.workout.active.ui.ActiveWorkoutScreen
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.workout.preview.ui.WorkoutPreviewScreen
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.workout.summary.ui.WorkoutSummaryScreen
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.ui.navigation.viewmodel.NavViewModel

@Composable
fun RepCraftNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: NavViewModel = viewModel(factory = NavViewModel.Factory)
    val onboardingComplete by viewModel.onboardingComplete.collectAsState(initial = false)
    val startDestination = if (onboardingComplete) Screen.Home.route else Screen.Onboarding.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onOnboardingComplete = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToPreview = { navController.navigate(Screen.WorkoutPreview.route) },
                onNavigateToOnboarding = { navController.navigate(Screen.Onboarding.route) }
            )
        }
        composable(Screen.WorkoutPreview.route) {
            WorkoutPreviewScreen(
                onNavigateToActiveWorkout = { navController.navigate(Screen.ActiveWorkout.route) },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.ActiveWorkout.route) {
            ActiveWorkoutScreen(
                onWorkoutComplete = { sessionId ->
                    navController.navigate(Screen.WorkoutSummary.createRoute(sessionId)) {
                        popUpTo(Screen.Home.route)
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.WorkoutSummary.ROUTE,
            arguments = listOf(navArgument("sessionId") { type = NavType.LongType })
        ) {
            val sessionId = it.savedStateHandle.get<Long>("sessionId") ?: 0L
            WorkoutSummaryScreen(
                sessionId = sessionId,
                onNavigateHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.ExerciseLibrary.route) {
            ExerciseLibraryScreen()
        }
        composable(Screen.Progress.route) {
            ProgressDashboardScreen(
                onNavigateToHistory = { navController.navigate(Screen.WorkoutHistory.route) }
            )
        }
        composable(Screen.WorkoutHistory.route) {
            WorkoutHistoryScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
