package com.micahnyabuto.coinsphere.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.micahnyabuto.coinsphere.ui.auth.Signin.LoginScreen
import com.micahnyabuto.coinsphere.ui.auth.Signup.SignupScreen
import com.micahnyabuto.coinsphere.ui.auth.viewmodel.AuthViewModel
import com.micahnyabuto.coinsphere.ui.screens.details.CoinDetailsScreen
import com.micahnyabuto.coinsphere.ui.screens.details.CoinDetailsViewModel
import com.micahnyabuto.coinsphere.ui.screens.favourite.FavouritesScreen
import com.micahnyabuto.coinsphere.ui.screens.favourite.FavouritesViewModel
import com.micahnyabuto.coinsphere.ui.screens.market.MarketScreen
import com.micahnyabuto.coinsphere.ui.screens.market.MarketViewModel
import com.micahnyabuto.coinsphere.ui.screens.news.NewsScreen
import com.micahnyabuto.coinsphere.ui.screens.search.SearchScreen
import com.micahnyabuto.coinsphere.ui.screens.settings.SettingsScreen

@Composable
fun MainGraph(){
    val navController= rememberNavController()
    val isLoggedIn = Firebase.auth.currentUser != null
    val firstPage = if (isLoggedIn) Destinations.Market.route else Destinations.SignIn.route
    val favouritesViewModel: FavouritesViewModel = hiltViewModel()
    val marketViewModel: MarketViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val coinDetailsViewModel: CoinDetailsViewModel = hiltViewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.orEmpty()
    //Do not  Show bottom navigation when
    val showBottomNavigation = currentRoute !in listOf(
        Destinations.Splash.route,
        Destinations.SignUp.route,
        Destinations.SignIn.route,
        Destinations.Details.route,
    )
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal),
        bottomBar = {
            if (showBottomNavigation) {
                Column {
                    HorizontalDivider(thickness = 2.dp)
                    NavigationBar(
                        tonalElevation = 0.dp,
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        BottomNavigation.entries.forEach { navigationItem ->

                            val isSelected = currentRoute == navigationItem.route

                            NavigationBarItem(
                                selected = isSelected,
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) navigationItem.selectedIcon else navigationItem.unselectedIcon,
                                        contentDescription = navigationItem.label
                                    )
                                },
                                label = {
                                    Text(
                                        text = navigationItem.label,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 10.sp,
                                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                        )
                                    )
                                },
                                onClick = {
                                    if (currentRoute != navigationItem.route) {
                                        navController.navigate(navigationItem.route)
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Transparent,
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }

                }
            }
        }

    ){innerpadding->
        NavHost(
            navController = navController,
            startDestination = Destinations.Market.route,
            modifier = Modifier.padding(innerpadding)
        ) {
            composable(Destinations.Market.route) {
                MarketScreen(
                    navController = navController
                )
            }

            composable(
                Destinations.Details.route,
                arguments = listOf(navArgument("coinName") { type = NavType.StringType })
            ) { backStackEntry ->
                val coinName = backStackEntry.arguments?.getString("coinName")
                val allCoins by marketViewModel.allCoins.collectAsState()
                LaunchedEffect(
                    Unit
                ) {
                    coinDetailsViewModel.getDetails()
                }
                val coin = allCoins.find { it.name == coinName }
                if (coin != null) {
                    CoinDetailsScreen(
                        coinDetailsViewModel = coinDetailsViewModel

                    )
                } else {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Feature is coming soon")
                    }
                }
            }
            composable(Destinations.SignUp.route) {
                SignupScreen(
                    authViewModel = authViewModel,
                    navController = navController
                )
            }
            composable(Destinations.SignIn.route) {
                LoginScreen(
                    navController = navController
                )
            }

            composable(Destinations.News.route) {
                NewsScreen(
                    navController = navController
                )
            }
            composable(Destinations.Favourite.route) {
                FavouritesScreen(
                    favouritesViewModel = favouritesViewModel,
                    marketViewModel = marketViewModel,
                    navController = navController
                )
            }
            composable(Destinations.Search.route) {
                SearchScreen(
                    navController = navController
                )
            }
            composable(Destinations.Settings.route) {
                SettingsScreen()
            }




        }
    }
}