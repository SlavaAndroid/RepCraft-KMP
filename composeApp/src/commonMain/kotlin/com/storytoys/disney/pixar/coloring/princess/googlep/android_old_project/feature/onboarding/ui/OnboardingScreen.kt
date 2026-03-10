package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.onboarding.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.Equipment
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.enums.ExperienceLevel
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.Goal
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.enums.MuscleGroup

@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit,
    vm: OnboardingViewModel = viewModel(factory = OnboardingViewModel.Companion.Factory)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        // Animated progress dots
        StepDotsIndicator(
            currentStep = vm.currentStep,
            totalSteps = vm.totalSteps
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Animated step content
        AnimatedContent(
            targetState = vm.currentStep,
            modifier = Modifier.weight(1f),
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInHorizontally { w -> w } + fadeIn(tween(300))) togetherWith
                            (slideOutHorizontally { w -> -w } + fadeOut(tween(200)))
                } else {
                    (slideInHorizontally { w -> -w } + fadeIn(tween(300))) togetherWith
                            (slideOutHorizontally { w -> w } + fadeOut(tween(200)))
                }
            },
            label = "onboarding_step"
        ) { step ->
            Box(modifier = Modifier.fillMaxSize()) {
                when (step) {
                    0 -> WelcomeStep()
                    1 -> EquipmentStep(selected = vm.selectedEquipment, onToggle = vm::toggleEquipment)
                    2 -> GoalStep(selected = vm.selectedGoal, onSelect = vm::setGoal)
                    3 -> ExperienceStep(selected = vm.selectedLevel, onSelect = vm::setLevel)
                    4 -> MusclesStep(selected = vm.selectedMuscles, onToggle = vm::toggleMuscle)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Navigation buttons
        val isLast = vm.currentStep == vm.totalSteps - 1
        if (vm.currentStep > 0) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = vm::prevStep,
                    modifier = Modifier.weight(1f).height(54.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text("Back")
                }
                Button(
                    onClick = {
                        if (!isLast) vm.nextStep()
                        else vm.complete(onOnboardingComplete)
                    },
                    modifier = Modifier.weight(1f).height(54.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(if (!isLast) "Continue" else "Start Training")
                }
            }
        } else {
            Button(
                onClick = vm::nextStep,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Get Started")
            }
        }
    }
}

@Composable
private fun StepDotsIndicator(currentStep: Int, totalSteps: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalSteps) { i ->
            val isActive = i == currentStep
            val isDone = i < currentStep
            val width by animateDpAsState(
                targetValue = if (isActive) 28.dp else 8.dp,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                label = "dot_width_$i"
            )
            val color = when {
                isDone || isActive -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.surfaceVariant
            }
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(width)
                    .clip(CircleShape)
                    .background(color)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "${currentStep + 1} / $totalSteps",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun WelcomeStep() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) { visible = true }

        AnimatedVisibility(
            visible = visible,
            enter = scaleIn(spring(dampingRatio = Spring.DampingRatioMediumBouncy)) + fadeIn()
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("💪", style = MaterialTheme.typography.displayMedium)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically { it / 2 } + fadeIn(tween(400, delayMillis = 150))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Strong Wings",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Your personal home workout companion",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically { it / 2 } + fadeIn(tween(400, delayMillis = 300))
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureRow("🏋️", "Equipment-aware workouts")
                    FeatureRow("🎯", "Goal-based programming")
                    FeatureRow("📈", "Progressive overload built in")
                    FeatureRow("📱", "100% offline – no internet needed")
                }
            }
        }
    }
}

@Composable
private fun FeatureRow(emoji: String, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = emoji, style = MaterialTheme.typography.titleMedium)
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun EquipmentStep(selected: Set<Equipment>, onToggle: (Equipment) -> Unit) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        StepTitle("What equipment\ndo you have?", "Select all that apply")
        Spacer(modifier = Modifier.height(20.dp))
        val options = listOf(
            Equipment.NO_EQUIPMENT to "Bodyweight only",
            Equipment.DUMBBELLS to "Dumbbells",
            Equipment.RESISTANCE_BANDS to "Resistance Bands",
            Equipment.PULL_UP_BAR to "Pull-Up Bar",
            Equipment.BENCH to "Bench / Chair",
            Equipment.KETTLEBELL to "Kettlebell",
            Equipment.BARBELL to "Barbell",
            Equipment.YOGA_MAT to "Yoga Mat"
        )
        options.forEachIndexed { i, (equip, label) ->
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) { visible = true }
            AnimatedVisibility(
                visible = visible,
                enter = slideInHorizontally { -it } + fadeIn(tween(250, delayMillis = i * 50))
            ) {
                ToggleCard(
                    label = label,
                    selected = equip in selected,
                    onClick = { onToggle(equip) }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun GoalStep(selected: Goal, onSelect: (Goal) -> Unit) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        StepTitle("What's your goal?", "This shapes your sets, reps & rest times")
        Spacer(modifier = Modifier.height(20.dp))
        listOf(
            Triple(Goal.BUILD_MUSCLE, "💪", "Build Muscle\n4 sets · 8-12 reps · Heavy"),
            Triple(Goal.LOSE_WEIGHT, "🔥", "Lose Weight\nHIIT circuits · Short rest"),
            Triple(Goal.IMPROVE_ENDURANCE, "⚡", "Endurance\nHigh reps · Cardio focus"),
            Triple(Goal.INCREASE_FLEXIBILITY, "🧘", "Flexibility\nStretching · Mobility holds"),
            Triple(Goal.GENERAL_FITNESS, "🏃", "General Fitness\nBalanced 3×12 approach")
        ).forEachIndexed { i, (goal, emoji, desc) ->
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) { visible = true }
            AnimatedVisibility(
                visible = visible,
                enter = slideInHorizontally { -it } + fadeIn(tween(250, delayMillis = i * 60))
            ) {
                SelectionCard(
                    emoji = emoji,
                    description = desc,
                    selected = goal == selected,
                    onClick = { onSelect(goal) }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ExperienceStep(selected: ExperienceLevel, onSelect: (ExperienceLevel) -> Unit) {
    Column {
        StepTitle("Experience level?", "Determines exercise complexity and volume")
        Spacer(modifier = Modifier.height(20.dp))
        listOf(
            Triple(ExperienceLevel.BEGINNER, "🌱", "Beginner\nNew to working out or returning after a break"),
            Triple(ExperienceLevel.INTERMEDIATE, "🌿", "Intermediate\nTraining consistently for 6+ months"),
            Triple(ExperienceLevel.ADVANCED, "🌳", "Advanced\nYears of consistent training experience")
        ).forEachIndexed { i, (level, emoji, desc) ->
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) { visible = true }
            AnimatedVisibility(
                visible = visible,
                enter = slideInHorizontally { -it } + fadeIn(tween(300, delayMillis = i * 80))
            ) {
                SelectionCard(
                    emoji = emoji,
                    description = desc,
                    selected = level == selected,
                    onClick = { onSelect(level) }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun MusclesStep(selected: Set<MuscleGroup>, onToggle: (MuscleGroup) -> Unit) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        StepTitle("Focus areas", "Optional · leave empty for full body")
        Spacer(modifier = Modifier.height(20.dp))
        val muscles = listOf(
            MuscleGroup.CHEST to "Chest",
            MuscleGroup.BACK to "Back",
            MuscleGroup.SHOULDERS to "Shoulders",
            MuscleGroup.BICEPS to "Biceps",
            MuscleGroup.TRICEPS to "Triceps",
            MuscleGroup.CORE to "Core / Abs",
            MuscleGroup.QUADS to "Quads",
            MuscleGroup.HAMSTRINGS to "Hamstrings",
            MuscleGroup.GLUTES to "Glutes",
            MuscleGroup.CALVES to "Calves"
        )
        muscles.chunked(2).forEach { pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                pair.forEach { (muscle, label) ->
                    FilterChip(
                        selected = muscle in selected,
                        onClick = { onToggle(muscle) },
                        label = { Text(label) },
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.large
                    )
                }
                if (pair.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun StepTitle(title: String, subtitle: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun ToggleCard(label: String, selected: Boolean, onClick: () -> Unit) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.02f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "toggle_scale"
    )
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().scale(scale),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.large,
        border = if (selected) BorderStroke(
            2.dp, MaterialTheme.colorScheme.primary
        ) else null
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
            AnimatedVisibility(
                visible = selected,
                enter = scaleIn(spring(dampingRatio = Spring.DampingRatioLowBouncy)) + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("✓", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectionCard(emoji: String, description: String, selected: Boolean, onClick: () -> Unit) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.02f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "sel_scale"
    )
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().scale(scale),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.large,
        border = if (selected) BorderStroke(
            2.dp, MaterialTheme.colorScheme.primary
        ) else null
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(emoji, style = MaterialTheme.typography.headlineSmall)
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
            RadioButton(selected = selected, onClick = null)
        }
    }
}
