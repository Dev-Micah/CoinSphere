package com.micahnyabuto.coinsphere.ui.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavigation(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
){

    Market(
        label = "Market",
        selectedIcon = Icons.Default.BarChart,
        unselectedIcon = Icons.Default.BarChart,
        route = Destinations.Market.route
    ),

//    News(
//        label = "News",
//        selectedIcon = Icons.Default.Article,
//        unselectedIcon = Icons.Default.Article,
//        route = Destinations.News
//    ),

//    Favourite(
//        label ="Favourites",
//        selectedIcon = Icons.Default.Star,
//        unselectedIcon = Icons.Default.Star,
//        route = Destinations.Favourite
//
//    ),

    Search(
        label ="Search",
        selectedIcon = Icons.Default.Search,
        unselectedIcon = Icons.Default.Search,
        route = Destinations.Search.route
    ),

    Settings(
        label = "Settings",
        selectedIcon = Icons.Default.Settings,
        unselectedIcon = Icons.Default.Settings,
        route = Destinations.Settings.route
    )

}