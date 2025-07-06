package com.micahnyabuto.coinsphere.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import com.micahnyabuto.coinsphere.ui.screens.splash.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    val isLoggedIn = Firebase.auth.currentUser!=null
    val firstPage = if (isLoggedIn) Destinations.Market else Destinations.SignIn
    val favouritesViewModel: FavouritesViewModel = hiltViewModel()
    val marketViewModel: MarketViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val coinDetailsViewModel: CoinDetailsViewModel =hiltViewModel()


    NavHost (
         modifier =modifier,
         navController = navController,
         startDestination = Destinations.Splash.route,

     ){
        composable (Destinations.Splash.route){
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Destinations.Market.route){
                        popUpTo(Destinations.Splash.route){
                            inclusive =true
                        }
                    }

                }
            )
        }
        composable (Destinations.Market.route){
            MarketScreen(
                navController =navController
            )
        }

        composable (
            Destinations.Details.route,
            arguments = listOf(navArgument("coinName") {type = NavType.StringType})
        ){backStackEntry->
            val coinName = backStackEntry.arguments?.getString("coinName")
            val allCoins by marketViewModel.allCoins.collectAsState()
            LaunchedEffect (
                Unit
            ){
                coinDetailsViewModel.getDetails()
            }
            val coin = allCoins.find{it.name == coinName}
            if (coin != null){
            CoinDetailsScreen(
                coinDetailsViewModel = coinDetailsViewModel

            )
        }else{
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Feature is coming soon")
                }
        }
        }
        composable (Destinations.SignUp.route){
            SignupScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }
        composable (Destinations.SignIn.route){
            LoginScreen(
                navController =navController
            )
        }

        composable (Destinations.News.route){
            NewsScreen(
                navController =navController
            )
        }
        composable (Destinations.Favourite.route){
            FavouritesScreen(
                favouritesViewModel = favouritesViewModel,
                marketViewModel = marketViewModel,
                navController =navController
            )
        }
        composable (Destinations.Search.route){
            SearchScreen(
                navController =navController
            )
        }
        composable (Destinations.Settings.route){
            SettingsScreen()
        }



     }
}