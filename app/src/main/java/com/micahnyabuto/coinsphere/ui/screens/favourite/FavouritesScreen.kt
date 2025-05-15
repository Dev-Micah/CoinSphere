package com.micahnyabuto.coinsphere.ui.screens.favourite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.micahnyabuto.coinsphere.ui.screens.market.CoinsRow
import com.micahnyabuto.coinsphere.ui.screens.market.MarketViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun FavouritesScreen(
    favouritesViewModel: FavouritesViewModel = hiltViewModel(),
    marketViewModel: MarketViewModel = hiltViewModel(),
    navController : NavController
) {

    val allCoins by marketViewModel.allCoins.collectAsState()
    LaunchedEffect(allCoins) {
        favouritesViewModel.setAllCoins(allCoins)
    }

    FavouritesScreenContent()



}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreenContent(
    favouritesViewModel: FavouritesViewModel =hiltViewModel()
){
    val navController = rememberNavController()
    val favouriteCoins by favouritesViewModel.favouriteCoins.collectAsState()
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text("Favourites")
                }
            )
        }
    ){
        innerpadding ->
        LazyColumn(
            modifier = Modifier.padding(innerpadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(favouriteCoins){ coin ->
                CoinsRow(
                    coin =coin,
                    navController = navController
                )
            }
        }
    }

}
