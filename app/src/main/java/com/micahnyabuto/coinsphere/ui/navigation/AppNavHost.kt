package com.micahnyabuto.coinsphere.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.micahnyabuto.coinsphere.ui.screens.favourite.FavouritesScreen
import com.micahnyabuto.coinsphere.ui.screens.market.MarketScreen
import com.micahnyabuto.coinsphere.ui.screens.news.NewsScreen
import com.micahnyabuto.coinsphere.ui.screens.search.SearchScreen
import com.micahnyabuto.coinsphere.ui.screens.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost (
         modifier =modifier,
         navController = navController,
         startDestination = Destinations.Market
     ){
        composable <Destinations.Market>{
            MarketScreen()
        }
        composable <Destinations.News>{
            NewsScreen()
        }
        composable <Destinations.Favourite>{
            FavouritesScreen()
        }
        composable <Destinations.Search>{
            SearchScreen()
        }
        composable <Destinations.Settings>{
            SettingsScreen(
                navController =navController
            )
        }

     }
}