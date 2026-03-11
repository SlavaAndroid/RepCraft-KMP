package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.workout.preview.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.PlannedExercise
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.WorkoutPhase
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutPreviewScreen(
    onNavigateToActiveWorkout: () -> Unit,
    onNavigateBack: () -> Unit,
    vm: WorkoutPreviewViewModel = viewModel(factory = WorkoutPreviewViewModel.Companion.Factory)
) {
    val plan = vm.plan

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Today's Workout", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { vm.regenerate() }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Regenerate")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            Surface(
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.background
            ) {
                Button(
                    onClick = onNavigateToActiveWorkout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(58.dp),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Text("Start Workout 🚀", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (plan == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No workout generated. Go back and try again.")
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                // Summary banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                    MaterialTheme.colorScheme.background
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SummaryPill(value = "${plan.durationMinutes}", label = "min")
                        SummaryPill(value = "${plan.main.size}", label = "exercises")
                        SummaryPill(value = "${plan.main.sumOf { it.sets }}", label = "sets")
                    }
                }
            }

            if (plan.warmUp.isNotEmpty()) {
                item { SectionLabel(title = "Warm-Up", count = plan.warmUp.size, emoji = "🔆") }
                itemsIndexed(plan.warmUp, key = { _, ex -> ex.exercise.id }) { i, ex ->
                    AnimatedExerciseCard(exercise = ex, index = i, phase = WorkoutPhase.WARM_UP)
                }
            }

            if (plan.main.isNotEmpty()) {
                item { SectionLabel(title = "Main Workout", count = plan.main.size, emoji = "⚡") }
                itemsIndexed(plan.main, key = { _, ex -> ex.exercise.id }) { i, ex ->
                    AnimatedExerciseCard(exercise = ex, index = i + plan.warmUp.size, phase = WorkoutPhase.MAIN)
                }
            }

            if (plan.cooldown.isNotEmpty()) {
                item { SectionLabel(title = "Cool-Down", count = plan.cooldown.size, emoji = "🧊") }
                itemsIndexed(plan.cooldown, key = { _, ex -> ex.exercise.id }) { i, ex ->
                    AnimatedExerciseCard(exercise = ex, index = i + plan.warmUp.size + plan.main.size, phase = WorkoutPhase.COOLDOWN)
                }
            }
        }
    }
}

@Composable
private fun SummaryPill(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SectionLabel(title: String, count: Int, emoji: String) {
    Row(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(emoji, style = MaterialTheme.typography.titleMedium)
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Text(
                text = "$count",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun AnimatedExerciseCard(exercise: PlannedExercise, index: Int, phase: WorkoutPhase) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(index * 10L)
        visible = true
    }
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally { it / 3 } + fadeIn(tween(300))
    ) {
        ExerciseCard(
            planned = exercise,
            phase = phase,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun ExerciseCard(planned: PlannedExercise, phase: WorkoutPhase, modifier: Modifier = Modifier) {
    val ex = planned.exercise

    val containerColor = when (phase) {
        WorkoutPhase.WARM_UP -> MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f)
        WorkoutPhase.COOLDOWN -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
        WorkoutPhase.MAIN -> MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Exercise icon dot
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                val icon = when {
                    ex.muscleGroups.any { it.name == "CHEST" || it.name == "SHOULDERS" || it.name == "TRICEPS" } -> "💪"
                    ex.muscleGroups.any { it.name == "BACK" || it.name == "BICEPS" } -> "🏋️"
                    ex.muscleGroups.any { it.name == "CORE" } -> "⚡"
                    ex.muscleGroups.any { it.name == "QUADS" || it.name == "GLUTES" || it.name == "HAMSTRINGS" } -> "🦵"
                    phase == WorkoutPhase.WARM_UP -> "🔆"
                    phase == WorkoutPhase.COOLDOWN -> "🧘"
                    else -> "🏃"
                }
                Text(icon, style = MaterialTheme.typography.titleMedium)
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = ex.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = ex.muscleGroups.take(2).joinToString(" · ") {
                        it.name.lowercase().replaceFirstChar { c -> c.uppercase() }
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                if (phase == WorkoutPhase.MAIN) {
                    Text(
                        text = "Rest ${planned.restAfterSeconds}s between sets",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            val detail = when {
                phase != WorkoutPhase.MAIN -> "${planned.durationSeconds ?: 30}s"
                planned.durationSeconds != null -> "${planned.sets}×${planned.durationSeconds}s"
                else -> "${planned.sets}×${planned.reps}"
            }
            Surface(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = detail,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
