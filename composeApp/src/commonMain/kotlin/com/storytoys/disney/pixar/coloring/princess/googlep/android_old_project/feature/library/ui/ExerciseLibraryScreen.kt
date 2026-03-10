package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.library.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.Exercise
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.Equipment
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.MuscleGroup
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseLibraryScreen(
    vm: ExerciseLibraryViewModel = viewModel(factory = ExerciseLibraryViewModel.Companion.Factory)
) {
    val selectedExercise = vm.selectedExercise

    AnimatedContent(
        targetState = selectedExercise,
        transitionSpec = {
            if (targetState != null) {
                slideInHorizontally { it } + fadeIn(tween(300)) togetherWith
                        slideOutHorizontally { -it } + fadeOut(tween(200))
            } else {
                slideInHorizontally { -it } + fadeIn(tween(300)) togetherWith
                        slideOutHorizontally { it } + fadeOut(tween(200))
            }
        },
        label = "library_detail"
    ) { exercise ->
        if (exercise != null) {
            ExerciseDetailScreen(exercise = exercise, onBack = { vm.selectExercise(null) })
        } else {
            LibraryListScreen(vm = vm)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LibraryListScreen(vm: ExerciseLibraryViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exercise Library", fontWeight = FontWeight.Black) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Search bar
            OutlinedTextField(
                value = vm.searchQuery,
                onValueChange = vm::setSearch,
                placeholder = { Text("Search exercises…") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                trailingIcon = {
                    AnimatedVisibility(visible = vm.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { vm.setSearch("") }) {
                            Icon(Icons.Filled.Close, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                singleLine = true,
                shape = MaterialTheme.shapes.extraLarge
            )

            // Muscle filter chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = vm.selectedMuscle == null,
                        onClick = { vm.setMuscleFilter(null) },
                        label = { Text("All") },
                        shape = MaterialTheme.shapes.large
                    )
                }
                items(MuscleGroup.values().toList()) { muscle ->
                    FilterChip(
                        selected = vm.selectedMuscle == muscle,
                        onClick = { vm.setMuscleFilter(if (vm.selectedMuscle == muscle) null else muscle) },
                        label = { Text(muscle.name.lowercase().replaceFirstChar { it.uppercase() }) },
                        shape = MaterialTheme.shapes.large
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Equipment filter chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = vm.selectedEquipment == null,
                        onClick = { vm.setEquipmentFilter(null) },
                        label = { Text("Any Equipment") },
                        shape = MaterialTheme.shapes.large
                    )
                }
                items(Equipment.values().toList()) { equip ->
                    FilterChip(
                        selected = vm.selectedEquipment == equip,
                        onClick = { vm.setEquipmentFilter(if (vm.selectedEquipment == equip) null else equip) },
                        label = { Text(equip.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }) },
                        shape = MaterialTheme.shapes.large
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedContent(
                targetState = vm.filteredExercises.size,
                transitionSpec = { fadeIn(tween(200)) togetherWith fadeOut(tween(150)) },
                label = "count_anim"
            ) { count ->
                Text(
                    text = "$count exercises",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(vm.filteredExercises, key = { _, ex -> ex.id }) { index, exercise ->
                    var visible by remember(exercise.id) { mutableStateOf(false) }
                    LaunchedEffect(exercise.id) {
                        delay(index * 40L)
                        visible = true
                    }
                    AnimatedVisibility(
                        visible = visible,
                        enter = slideInHorizontally { it / 3 } + fadeIn(tween(250))
                    ) {
                        ExerciseListCard(exercise = exercise, onClick = { vm.selectExercise(exercise) })
                    }
                }
            }
        }
    }
}

@Composable
private fun ExerciseListCard(exercise: Exercise, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = exercise.muscleGroups.firstOrNull()?.let {
                        when (it) {
                            MuscleGroup.CHEST, MuscleGroup.TRICEPS -> "💪"
                            MuscleGroup.BACK, MuscleGroup.BICEPS -> "🏋️"
                            MuscleGroup.CORE -> "⚡"
                            MuscleGroup.QUADS, MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES, MuscleGroup.CALVES -> "🦵"
                            MuscleGroup.SHOULDERS -> "🔺"
                            MuscleGroup.FULL_BODY -> "🏃"
                        }
                    } ?: "💪",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = exercise.muscleGroups.take(2).joinToString(" · ") {
                        it.name.lowercase().replaceFirstChar { c -> c.uppercase() }
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            val equip = exercise.requiredEquipment.firstOrNull()
            if (equip != null && equip.name != "NO_EQUIPMENT") {
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        text = equip.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExerciseDetailScreen(exercise: Exercise, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(exercise.name, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                // Hero card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                        MaterialTheme.colorScheme.primaryContainer
                                    )
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column {
                            Text(
                                text = exercise.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                exercise.muscleGroups.forEach { mg ->
                                    Surface(
                                        shape = MaterialTheme.shapes.large,
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = mg.name.lowercase().replaceFirstChar { it.uppercase() },
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Details", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        DetailRow("Equipment", exercise.requiredEquipment.joinToString(", ") {
                            it.name.replace("_", " ").lowercase().replaceFirstChar { c -> c.uppercase() }
                        })
                        DetailRow("Difficulty", exercise.experienceLevels.joinToString(", ") {
                            it.name.lowercase().replaceFirstChar { c -> c.uppercase() }
                        })
                        DetailRow("Default Sets", "${exercise.defaultSets}")
                        if (exercise.defaultReps != null) DetailRow("Default Reps", "${exercise.defaultReps}")
                        if (exercise.durationSeconds != null) DetailRow("Duration", "${exercise.durationSeconds}s")
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Step-by-step", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        exercise.instructionSteps.forEachIndexed { i, step ->
                            var visible by remember { mutableStateOf(false) }
                            LaunchedEffect(i) { delay(i * 80L); visible = true }
                            AnimatedVisibility(
                                visible = visible,
                                enter = slideInHorizontally { -it / 2 } + fadeIn(tween(250))
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${i + 1}",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        text = step,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
    }
}
