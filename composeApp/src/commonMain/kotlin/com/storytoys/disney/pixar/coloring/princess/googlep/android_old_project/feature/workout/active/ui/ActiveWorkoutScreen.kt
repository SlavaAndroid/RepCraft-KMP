package com.storytoys.disney.pixar.coloring.princess.googlep.feature.workout.active.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.storytoys.disney.pixar.coloring.princess.googlep.ui.platform.BackHandler

@Composable
fun ActiveWorkoutScreen(
    onWorkoutComplete: (Long) -> Unit,
    onNavigateBack: () -> Unit,
    vm: ActiveWorkoutViewModel = viewModel(factory = ActiveWorkoutViewModel.Factory)
) {
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler { showExitDialog = true }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Exit Workout?") },
            text = { Text("Your progress will not be saved if you exit now.") },
            confirmButton = {
                TextButton(onClick = { showExitDialog = false; onNavigateBack() }) { Text("Exit") }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) { Text("Keep Going") }
            },
            shape = MaterialTheme.shapes.extraLarge
        )
    }

    LaunchedEffect(vm.phase) {
        if (vm.phase == ActiveWorkoutViewModel.WorkoutPhase.COMPLETE) {
            vm.saveSession { sessionId -> onWorkoutComplete(sessionId) }
        }
    }

    AnimatedContent(
        targetState = vm.phase,
        transitionSpec = {
            fadeIn(tween(300)) togetherWith fadeOut(tween(200))
        },
        label = "workout_phase"
    ) { phase ->
        when (phase) {
            ActiveWorkoutViewModel.WorkoutPhase.COMPLETE -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Saving your workout\u2026", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            ActiveWorkoutViewModel.WorkoutPhase.RESTING -> {
                val ex = vm.currentExercise
                if (ex != null) {
                    RestScreen(
                        restTimeLeft = vm.restTimeLeft,
                        restTotal = ex.restAfterSeconds,
                        nextLabel = if (vm.currentSetNumber < ex.sets)
                            "Set ${vm.currentSetNumber + 1} of ${ex.sets}: ${ex.exercise.name}"
                        else {
                            val nextIdx = vm.currentExerciseIndex + 1
                            if (nextIdx < vm.totalExercises) "Next: ${vm.exercises[nextIdx].exercise.name}"
                            else "Final stretch \u2014 almost done!"
                        },
                        onSkipRest = vm::skipRest
                    )
                }
            }
            ActiveWorkoutViewModel.WorkoutPhase.EXERCISING -> {
                val ex = vm.currentExercise
                if (ex != null) {
                    ExercisingScreen(vm = vm, onExit = { showExitDialog = true })
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No workout loaded. Please go back and generate a workout.")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExercisingScreen(vm: ActiveWorkoutViewModel, onExit: () -> Unit) {
    val ex = vm.currentExercise ?: return
    val elapsedMins = vm.elapsedSeconds / 60
    val elapsedSecs = vm.elapsedSeconds % 60

    val overallProgress = (vm.currentExerciseIndex.toFloat()) / vm.totalExercises
    val animatedProgress by animateFloatAsState(
        targetValue = overallProgress,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "progress"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "${vm.currentExerciseIndex + 1} / ${vm.totalExercises}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        LinearProgressIndicator(
                            progress = { animatedProgress },
                            modifier = Modifier.width(120.dp).height(4.dp).clip(CircleShape),
                            strokeCap = StrokeCap.Round
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onExit) {
                        Icon(Icons.Filled.Close, contentDescription = "Exit")
                    }
                },
                actions = {
                    Surface(
                        shape = MaterialTheme.shapes.large,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "${elapsedMins}:${elapsedSecs.toString().padStart(2, '0')}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            AnimatedContent(
                targetState = ex.exercise.name,
                transitionSpec = {
                    slideInVertically { -it / 2 } + fadeIn(tween(250)) togetherWith
                            slideOutVertically { it / 2 } + fadeOut(tween(200))
                },
                label = "exercise_name"
            ) { name ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = ex.exercise.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ex.exercise.muscleGroups.take(3).forEach { mg ->
                    AssistChip(
                        onClick = {},
                        label = {
                            Text(
                                mg.name.lowercase().replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        modifier = Modifier.padding(horizontal = 4.dp),
                        shape = MaterialTheme.shapes.large
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (vm.isTimedExercise) {
                TimedExerciseDisplay(
                    timeLeft = vm.exerciseTimeLeft,
                    totalTime = ex.durationSeconds ?: 30,
                    currentSet = vm.currentSetNumber,
                    totalSets = ex.sets
                )
            } else {
                RepExerciseDisplay(
                    reps = ex.reps ?: 12,
                    currentSet = vm.currentSetNumber,
                    totalSets = ex.sets
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "How to do it",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    ex.exercise.instructionSteps.forEachIndexed { i, step ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${i + 1}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = step,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            var buttonPressed by remember { mutableStateOf(false) }
            val buttonScale by animateFloatAsState(
                targetValue = if (buttonPressed) 0.95f else 1f,
                animationSpec = spring(Spring.DampingRatioMediumBouncy),
                label = "btn_scale"
            )

            Button(
                onClick = {
                    buttonPressed = true
                    vm.onSetComplete()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .scale(buttonScale),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Icon(Icons.Filled.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = if (vm.currentSetNumber < ex.sets) "Done \u2013 Start Rest" else "Done \u2013 Next Exercise",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun TimedExerciseDisplay(timeLeft: Int, totalTime: Int, currentSet: Int, totalSets: Int) {
    val progress = if (totalTime > 0) timeLeft.toFloat() / totalTime else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(800, easing = LinearEasing),
        label = "timer_progress"
    )

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxSize(),
            strokeWidth = 10.dp,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeCap = StrokeCap.Round
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$timeLeft",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = "seconds", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Set $currentSet / $totalSets", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun RepExerciseDisplay(reps: Int, currentSet: Int, totalSets: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(32.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Set $currentSet / $totalSets",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedContent(
                targetState = reps,
                transitionSpec = {
                    scaleIn(spring(Spring.DampingRatioMediumBouncy)) + fadeIn() togetherWith
                            scaleOut() + fadeOut()
                },
                label = "reps_anim"
            ) { r ->
                Text(
                    text = "$r",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(text = "reps", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}

@Composable
private fun RestScreen(restTimeLeft: Int, restTotal: Int, nextLabel: String, onSkipRest: () -> Unit) {
    val progress = if (restTotal > 0) restTimeLeft.toFloat() / restTotal else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(800, easing = LinearEasing),
        label = "rest_progress"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(text = "Rest", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.tertiary)

            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(180.dp)) {
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 10.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    strokeCap = StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$restTimeLeft",
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(text = "seconds", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Coming up", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(text = nextLabel, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 4.dp))
                }
            }

            OutlinedButton(
                onClick = onSkipRest,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Icon(Icons.Filled.SkipNext, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Skip Rest", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}