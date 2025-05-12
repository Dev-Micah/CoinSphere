package com.micahnyabuto.coinsphere.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.micahnyabuto.coinsphere.data.remote.Coin
import com.micahnyabuto.coinsphere.ui.screens.market.CoinsRow
import com.micahnyabuto.coinsphere.ui.screens.market.MarketShimmerList
import com.micahnyabuto.coinsphere.ui.screens.market.MarketViewModel
import com.micahnyabuto.coinsphere.ui.screens.market.UiState
import java.nio.file.WatchEvent


@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MarketViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredCoins by viewModel.filteredCoins.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCoins()
    }
    Scaffold(
        modifier = Modifier.padding(top = 26.dp),
        topBar = {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                placeholder = { Text("Search coins") },
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(6.dp)

            )

        }
    ) { innerpadding ->
        when (uiState) {
            is UiState.Loading -> {
                MarketShimmerList()
            }

            is UiState.Success -> {

                LazyColumn(
                    modifier = Modifier.padding(
                        top = 90.dp,
                        start = 8.dp,
                        end = 1.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(filteredCoins) { coin ->
                        CoinsRow(
                            modifier = modifier,
                            coin = coin,
                            navController = navController
                        )


                    }
                }
            }
            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Failed to load 🥲")
                }
            }

        }
    }
}
