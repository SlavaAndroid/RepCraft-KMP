package com.storytoys.disney.pixar.coloring.princess.googlep.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BlazeRed,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF4A0A0A),
    onPrimaryContainer = BlazeRedLight,
    secondary = EmberOrange,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF3D1A00),
    onSecondaryContainer = EmberOrangeLight,
    tertiary = EnergyGreen,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF003322),
    onTertiaryContainer = EnergyGreen,
    background = SurfaceBlack,
    onBackground = Color.White,
    surface = Surface900,
    onSurface = Color(0xFFE8E8E8),
    surfaceVariant = Surface700,
    onSurfaceVariant = Color(0xFFAAAAAA),
    outline = Surface600,
    outlineVariant = Surface700,
    inverseSurface = Color(0xFFE8E8E8),
    inverseOnSurface = Surface900,
    inversePrimary = BlazeRedDark,
    surfaceTint = BlazeRed,
    error = Color(0xFFFF5252),
    onError = Color.White,
    errorContainer = Color(0xFF3D0000),
    onErrorContainer = Color(0xFFFF8A80)
)

private val LightColorScheme = lightColorScheme(
    primary = BlazeRedDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDAD6),
    onPrimaryContainer = Color(0xFF410002),
    secondary = EmberOrange,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFDCC5),
    onSecondaryContainer = Color(0xFF3A1200),
    tertiary = EnergyGreenDark,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFCCFFE5),
    onTertiaryContainer = Color(0xFF002116),
    background = SurfaceWhite,
    onBackground = Color(0xFF1A1A1A),
    surface = SurfaceWhite,
    onSurface = Color(0xFF1A1A1A),
    surfaceVariant = SurfaceLight200,
    onSurfaceVariant = Color(0xFF555555),
    outline = Color(0xFFBBBBBB)
)

@Composable
fun RepCraftTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = RepCraftShapes,
        content = content
    )
}