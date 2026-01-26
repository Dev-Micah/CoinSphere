package com.micahnyabuto.coinsphere.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.micahnyabuto.coinsphere.ui.navigation.Destinations
import com.micahnyabuto.coinsphere.ui.screens.market.CoinsRow
import com.micahnyabuto.coinsphere.ui.screens.market.MarketUiState
import com.micahnyabuto.coinsphere.ui.screens.market.MarketViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MarketViewModel = koinViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredCoins by viewModel.filteredCoins.collectAsState()
    val marketUiState by viewModel.marketUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCoins()
    }
    Scaffold(
        modifier = Modifier.padding(top = 35.dp),
        topBar = {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                placeholder = { Text("Search coins") },
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                )

            )

        }
    ) { innerpadding ->
        when (marketUiState) {
            is MarketUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerpadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is MarketUiState.Success -> {

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
                            coin = coin,
                        ){
                            navController.navigate(Destinations.Details.detailsRoute(coin.name))
                        }
                        HorizontalDivider()


                    }
                }
            }
            is MarketUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Failed to load")
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen(navController = NavController(LocalContext.current))
}