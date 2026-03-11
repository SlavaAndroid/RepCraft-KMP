package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.home.ui

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
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.domain.model.WorkoutSession
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.enums.ExperienceLevel
import kotlinx.coroutines.delay
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private const val PRIVACY_POLICY_URL = "https://doc-hosting.flycricket.io/strong-wings-privacy-policy/9cfa1a92-029e-4af8-b816-c784f666ca7a/privacy"
private const val TERMS_OF_USE_URL = "https://doc-hosting.flycricket.io/strong-wings-terms-of-use/8ba5554d-7523-45dd-940e-3b326a3abfd8/terms"

@Composable
fun HomeScreen(
    onNavigateToPreview: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
    vm: HomeViewModel = viewModel(factory = HomeViewModel.Companion.Factory)
) {
    val profile by vm.userProfile.collectAsStateWithLifecycle()
    val sessions by vm.recentSessions.collectAsStateWithLifecycle()
    val allSessions by vm.allSessions.collectAsStateWithLifecycle()

    val streak = vm.calculateStreak(allSessions)
    val thisWeek = vm.totalWorkoutsThisWeek(allSessions)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
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
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Column {
                    Text(
                        text = "Strong Wings",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Let's crush today's session \uD83D\uDCAA",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AnimatedStatCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.LocalFireDepartment,
                    targetValue = streak,
                    label = "Streak",
                    iconColor = MaterialTheme.colorScheme.error
                )
                AnimatedStatCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.FitnessCenter,
                    targetValue = thisWeek,
                    label = "This Week",
                    iconColor = MaterialTheme.colorScheme.secondary
                )
                AnimatedStatCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Timer,
                    targetValue = allSessions.size,
                    label = "Total",
                    iconColor = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Duration",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        val durations = listOf(10, 15, 20, 30, 45, 60)
                        items(durations) { min ->
                            val selected = vm.selectedDuration == min
                            val scale by animateFloatAsState(
                                targetValue = if (selected) 1.05f else 1f,
                                animationSpec = spring(Spring.DampingRatioMediumBouncy),
                                label = "dur_scale"
                            )
                            FilterChip(
                                selected = selected,
                                onClick = { vm.setDuration(min) },
                                label = {
                                    Text(
                                        "${min}m",
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                modifier = Modifier.scale(scale),
                                shape = MaterialTheme.shapes.large
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            val selectedLevel = profile?.experienceLevel ?: ExperienceLevel.BEGINNER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedLevel == ExperienceLevel.BEGINNER,
                    onClick = { vm.updateLevel(ExperienceLevel.BEGINNER) },
                    label = {
                        Text(
                            "Beginner",
                            fontWeight = if (selectedLevel == ExperienceLevel.BEGINNER) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = selectedLevel == ExperienceLevel.INTERMEDIATE,
                    onClick = { vm.updateLevel(ExperienceLevel.INTERMEDIATE) },
                    label = {
                        Text(
                            "Intermediate",
                            fontWeight = if (selectedLevel == ExperienceLevel.INTERMEDIATE) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }

        item {
            PulsingGenerateButton(
                enabled = profile != null,
                onClick = {
                    if (vm.generateWorkout()) onNavigateToPreview()
                }
            )
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }

        item {
            val uriHandler = LocalUriHandler.current
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = { uriHandler.openUri(PRIVACY_POLICY_URL) }) {
                    Text(
                        text = "Privacy Policy",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "·",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                TextButton(onClick = { uriHandler.openUri(TERMS_OF_USE_URL) }) {
                    Text(
                        text = "Terms of Use",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        if (sessions.isNotEmpty()) {
            item {
                Text(
                    text = "Recent Workouts",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
            }
            itemsIndexed(sessions) { index, session ->
                var visible by remember { mutableStateOf(false) }
                LaunchedEffect(index) {
                    delay(index * 80L)
                    visible = true
                }
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically { it / 2 } + fadeIn(tween(300))
                ) {
                    RecentSessionCard(
                        session = session,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        } else {
            item {
                EmptyStateCard(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

@Composable
private fun AnimatedStatCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    targetValue: Int,
    label: String,
    iconColor: Color
) {
    var displayValue by remember { mutableStateOf(0) }
    LaunchedEffect(targetValue) {
        val steps = minOf(targetValue, 20)
        if (steps > 0) {
            val increment = targetValue.toFloat() / steps
            for (i in 1..steps) {
                delay(30)
                displayValue = (increment * i).toInt()
            }
        }
        displayValue = targetValue
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "$displayValue",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PulsingGenerateButton(enabled: Boolean, onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (enabled) 1.03f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .scale(if (enabled) pulseScale else 1f),
            enabled = enabled,
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Generate Today's Workout",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun RecentSessionCard(session: WorkoutSession, modifier: Modifier = Modifier) {
    val ldt = session.completedAt.toLocalDateTime(TimeZone.currentSystemDefault())
    val month = ldt.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
    val hour = if (ldt.hour % 12 == 0) 12 else ldt.hour % 12
    val amPm = if (ldt.hour < 12) "AM" else "PM"
    val minute = ldt.minute.toString().padStart(2, '0')
    val dateStr = "$month ${ldt.dayOfMonth} \u00B7 $hour:$minute $amPm"
    val mins = session.durationSeconds / 60
    val secs = session.durationSeconds % 60

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text("\uD83C\uDFCB\uFE0F", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Workout Session",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = dateStr,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = if (secs > 0) "${mins}m ${secs}s" else "${mins}m",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${session.totalExercises} exercises",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun EmptyStateCard(modifier: Modifier = Modifier) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(spring(Spring.DampingRatioMediumBouncy)) + fadeIn(tween(400))
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(40.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("\uD83C\uDFCB\uFE0F", style = MaterialTheme.typography.displaySmall)
                Spacer(modifier = Modifier.height(12.dp))
                Text("No workouts yet", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(
                    text = "Generate your first workout above!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}