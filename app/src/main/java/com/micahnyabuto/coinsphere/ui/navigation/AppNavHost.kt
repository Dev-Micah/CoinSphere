package com.micahnyabuto.coinsphere.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.micahnyabuto.coinsphere.ui.screens.splash.SplashScreen

@Composable
fun AppNavHost(
) {
   val navController= rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.Splash.route,

        ) {
        composable(Destinations.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Destinations.Main.route) {
            MainGraph()
        }

    }
}