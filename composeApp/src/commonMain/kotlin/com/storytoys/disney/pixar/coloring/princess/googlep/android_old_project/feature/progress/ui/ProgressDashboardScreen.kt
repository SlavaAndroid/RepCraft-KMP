package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.feature.progress.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressDashboardScreen(
    onNavigateToHistory: () -> Unit,
    vm: ProgressViewModel = viewModel(factory = ProgressViewModel.Companion.Factory)
) {
    val sessions by vm.sessions.collectAsStateWithLifecycle()
    val streak = vm.calculateStreak(sessions)
    val thisWeek = vm.totalThisWeek(sessions)
    val totalMins = vm.totalMinutes(sessions)
    val avgMins = vm.avgDuration(sessions)
    val weeklyData = vm.weeklyData(sessions)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Progress", fontWeight = FontWeight.Black) },
                actions = {
                    IconButton(onClick = onNavigateToHistory) {
                        Icon(Icons.Filled.History, contentDescription = "History")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                // Streak hero card
                var visible by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) { visible = true }
                AnimatedVisibility(
                    visible = visible,
                    enter = scaleIn(spring(Spring.DampingRatioMediumBouncy)) + fadeIn(tween(400))
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
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
                                Text("🔥", style = MaterialTheme.typography.headlineSmall)
                            }
                            Column {
                                AnimatedCounterText(
                                    targetValue = streak,
                                    suffix = " days",
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "Current Streak",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }

            item {
                // 2×2 stats grid
                var visible by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) { delay(100); visible = true }
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically { it / 3 } + fadeIn(tween(350))
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            MiniStatCard(
                                modifier = Modifier.weight(1f),
                                emoji = "📅",
                                value = "$thisWeek",
                                label = "This Week"
                            )
                            MiniStatCard(
                                modifier = Modifier.weight(1f),
                                emoji = "🏋️",
                                value = "${sessions.size}",
                                label = "All Time"
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            MiniStatCard(
                                modifier = Modifier.weight(1f),
                                emoji = "⏱️",
                                value = "$totalMins",
                                label = "Total Minutes"
                            )
                            MiniStatCard(
                                modifier = Modifier.weight(1f),
                                emoji = "📊",
                                value = "$avgMins min",
                                label = "Avg Duration"
                            )
                        }
                    }
                }
            }

            item {
                // Weekly bar chart
                var chartVisible by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) { delay(200); chartVisible = true }
                AnimatedVisibility(
                    visible = chartVisible,
                    enter = slideInVertically { it / 3 } + fadeIn(tween(400))
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = "This Week",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            AnimatedBarChart(data = weeklyData)
                        }
                    }
                }
            }

            if (sessions.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(
                            modifier = Modifier.padding(40.dp).fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("📊", style = MaterialTheme.typography.displaySmall)
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("No data yet", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(
                                "Complete workouts to see your progress here.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimatedCounterText(
    targetValue: Int,
    suffix: String = "",
    style: TextStyle,
    fontWeight: FontWeight,
    color: Color
) {
    var displayValue by remember { mutableStateOf(0) }
    LaunchedEffect(targetValue) {
        val steps = minOf(targetValue, 30)
        if (steps > 0) {
            val inc = targetValue.toFloat() / steps
            for (i in 1..steps) {
                delay(20)
                displayValue = (inc * i).toInt()
            }
        }
        displayValue = targetValue
    }
    Text(
        text = "$displayValue$suffix",
        style = style,
        fontWeight = fontWeight,
        color = color
    )
}

@Composable
private fun MiniStatCard(modifier: Modifier = Modifier, emoji: String, value: String, label: String) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun AnimatedBarChart(data: List<Pair<String, Int>>) {
    val maxVal = data.maxOfOrNull { it.second }?.coerceAtLeast(1) ?: 1
    var animStarted by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(300)
        animStarted = true
    }
    val animatedHeights = data.map { (_, count) ->
        val targetFraction = count.toFloat() / maxVal
        val fraction by animateFloatAsState(
            targetValue = if (animStarted) targetFraction else 0f,
            animationSpec = tween(700, easing = FastOutSlowInEasing),
            label = "bar_height"
        )
        fraction
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceVariantColor = MaterialTheme.colorScheme.outline

    Row(
        modifier = Modifier.fillMaxWidth().height(110.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEachIndexed { index, (label, count) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                if (count > 0) {
                    Text(
                        text = "$count",
                        style = MaterialTheme.typography.labelSmall,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )
                }
                Canvas(
                    modifier = Modifier
                        .height(72.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                ) {
                    val barH = animatedHeights[index] * size.height
                    if (barH > 0f) {
                        drawRoundRect(
                            brush = Brush.verticalGradient(
                                listOf(primaryColor, primaryColor.copy(alpha = 0.6f))
                            ),
                            topLeft = Offset(0f, size.height - barH),
                            size = Size(size.width, barH),
                            cornerRadius = CornerRadius(8f, 8f)
                        )
                    } else {
                        drawRoundRect(
                            color = surfaceVariantColor.copy(alpha = 0.3f),
                            topLeft = Offset(0f, size.height - 6f),
                            size = Size(size.width, 6f),
                            cornerRadius = CornerRadius(3f, 3f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = label, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
