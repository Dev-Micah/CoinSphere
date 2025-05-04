package com.micahnyabuto.coinsphere.ui.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavigation(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: Any
){

    Market(
        label = "Market",
        selectedIcon = Icons.Default.BarChart,
        unselectedIcon = Icons.Default.BarChart,
        route = Destinations.Market
    ),
    Search(
        label ="Search",
        selectedIcon = Icons.Default.Search,
        unselectedIcon = Icons.Default.Search,
        route = Destinations.Search
    ),
    Discover(
        label = "Discover",
        selectedIcon = Icons.Default.Explore,
        unselectedIcon = Icons.Default.Explore,
        route = Destinations.Discover
    ),
    Settings(
        label = "Settings",
        selectedIcon = Icons.Default.Settings,
        unselectedIcon = Icons.Default.Settings,
        route = Destinations.Settings
    )

}