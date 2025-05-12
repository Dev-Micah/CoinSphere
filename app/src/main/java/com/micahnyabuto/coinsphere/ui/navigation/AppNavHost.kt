package com.micahnyabuto.coinsphere.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.micahnyabuto.coinsphere.data.remote.Coin
import com.micahnyabuto.coinsphere.ui.screens.details.CoinDetailsScreen
import com.micahnyabuto.coinsphere.ui.screens.favourite.FavouritesScreen
import com.micahnyabuto.coinsphere.ui.screens.favourite.FavouritesViewModel
import com.micahnyabuto.coinsphere.ui.screens.market.MarketScreen
import com.micahnyabuto.coinsphere.ui.screens.market.MarketViewModel
import com.micahnyabuto.coinsphere.ui.screens.news.NewsScreen
import com.micahnyabuto.coinsphere.ui.screens.search.SearchScreen
import com.micahnyabuto.coinsphere.ui.screens.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    val favouritesViewModel: FavouritesViewModel = hiltViewModel()
    val marketViewModel: MarketViewModel = hiltViewModel()

    NavHost (
         modifier =modifier,
         navController = navController,
         startDestination = Destinations.Market
     ){
        composable <Destinations.Market>{
            MarketScreen(
                navController =navController
            )
        }
        composable <Destinations.News>{
            NewsScreen()
        }
        composable <Destinations.Favourite>{
            FavouritesScreen(
                favouritesViewModel = favouritesViewModel,
                marketViewModel = marketViewModel
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

        composable <Destinations.Details>{
            CoinDetailsScreen()
        }

     }
}