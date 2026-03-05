package com.storytoys.disney.pixar.coloring.princess.googlep.ui.platform

import androidx.compose.runtime.Composable

/**
 * Multiplatform BackHandler.
 * Android: intercepts hardware back / gesture back.
 * iOS: no-op (iOS uses swipe-back navigation natively).
 */
@Composable
expect fun BackHandler(enabled: Boolean = true, onBack: () -> Unit)