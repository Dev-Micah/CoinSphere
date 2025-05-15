package com.micahnyabuto.coinsphere.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    val viewModel: CoinDetailsViewModel =hiltViewModel()


    NavHost (
         modifier =modifier,
         navController = navController,
         startDestination = Destinations.Splash,

     ){
        composable <Destinations.Splash>{
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Destinations.Market){
                        popUpTo(Destinations.Splash){
                            inclusive =true
                        }
                    }

                }
            )
        }
        composable <Destinations.Market>{
            MarketScreen(
                navController =navController
            )
        }

        composable <Destinations.Details>{
            CoinDetailsScreen(
                viewModel = viewModel

            )
        }
        composable <Destinations.SignUp>{
            SignupScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }
        composable <Destinations.SignIn>{
            LoginScreen(
                navController =navController
            )
        }

        composable <Destinations.News>{
            NewsScreen(
                navController =navController
            )
        }
        composable <Destinations.Favourite>{
            FavouritesScreen(
                favouritesViewModel = favouritesViewModel,
                marketViewModel = marketViewModel,
                navController =navController
            )
        }
        composable <Destinations.Search>{
            SearchScreen(
                navController =navController
            )
        }
        composable <Destinations.Settings>{
            SettingsScreen(
                navController =navController
            )
        }



     }
}