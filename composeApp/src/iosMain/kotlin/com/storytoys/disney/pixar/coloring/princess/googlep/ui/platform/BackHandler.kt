package com.storytoys.disney.pixar.coloring.princess.googlep.ui.platform

import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    // No-op on iOS — back navigation handled by native swipe gesture
}
