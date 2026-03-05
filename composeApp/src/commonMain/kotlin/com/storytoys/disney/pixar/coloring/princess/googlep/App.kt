package com.storytoys.disney.pixar.coloring.princess.googlep

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.storytoys.disney.pixar.coloring.princess.googlep.ui.navigation.RepCraftNavHost
import com.storytoys.disney.pixar.coloring.princess.googlep.ui.navigation.Screen
import com.storytoys.disney.pixar.coloring.princess.googlep.ui.theme.RepCraftTheme

private data class BottomNavItem(val screen: Screen, val label: String, val icon: @Composable () -> Unit)

@Composable
fun App() {
    RepCraftTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val bottomNavItems = listOf(
            BottomNavItem(Screen.Home, "Home") { Icon(Icons.Default.Home, contentDescription = "Home") },
            BottomNavItem(Screen.ExerciseLibrary, "Library") { Icon(Icons.Default.FitnessCenter, contentDescription = "Library") },
            BottomNavItem(Screen.Progress, "Progress") { Icon(Icons.Default.ShowChart, contentDescription = "Progress") }
        )

        val bottomNavRoutes = bottomNavItems.map { it.screen.route }.toSet()
        val showBottomBar = currentDestination?.route in bottomNavRoutes

        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar {
                        bottomNavItems.forEach { item ->
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true,
                                onClick = {
                                    navController.navigate(item.screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = item.icon,
                                label = { Text(item.label) }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            RepCraftNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
