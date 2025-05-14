//package com.micahnyabuto.coinsphere.ui.screens.details
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Star
//import androidx.compose.material3.Button
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.unit.dp
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.text.TextStyle
//import androidx.hilt.navigation.compose.hiltViewModel
//import coil.compose.rememberAsyncImagePainter
//import com.micahnyabuto.coinsphere.data.remote.Coin
//import com.micahnyabuto.coinsphere.ui.screens.market.MarketViewModel
//import com.micahnyabuto.coinsphere.ui.screens.market.UiState
//
//
//@Composable
//fun CoinDetailsScreen(
//    modifier: Modifier = Modifier,
//    viewModel: MarketViewModel = hiltViewModel(),
//){
//    val uiState by viewModel.uiState.collectAsState()
//
//    LaunchedEffect (Unit) {
//        viewModel.fetchCoins()
//    }
//
//    when (uiState) {
//        is UiState.Loading -> {
//            CircularProgressIndicator(
//                color = MaterialTheme.colorScheme.primary,
//                )
//        }
//
//        is UiState.Success -> {
//            CoinDetailsScreenContent(
//                coin = coin
//            )
//        }
//
//        is UiState.Error -> {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    "Something went wrong!",
//                    style = MaterialTheme.typography.titleLarge.copy()
//                )
//                Spacer(Modifier.height(25.dp))
//                Button(
//                    onClick = { viewModel.fetchCoins() },
//                    modifier = Modifier.padding(8.dp),
//                    shape = RoundedCornerShape(8.dp)
//                ) {
//                    Text("Refresh again")
//                }
//            }
//        }
//
//    }
//
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CoinDetailsScreenContent(
//    coin: Coin
//){
//    Scaffold (
//        topBar = {
//            CenterAlignedTopAppBar(
//                title ={
//                    Row {
//
//                        Image(
//                            painter = rememberAsyncImagePainter(coin.image),
//                            contentDescription = coin.name,
//                            modifier = Modifier.size(35.dp),
//                            contentScale = ContentScale.Crop
//                        )
//
//                        Text("${coin.name.uppercase()}")
//
//                        Spacer(Modifier.size(30.dp))
//
//                        IconButton(onClick = {}) {
//                            Icon(
//                                imageVector = Icons.Default.Star,
//                                contentDescription = "isFavourite"
//                            )
//                        }
//                    }
//                }
//            )
//        }
//    ){ innerPadding ->
//        LazyColumn (
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxWidth(),
//            contentPadding = PaddingValues(horizontal = 16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//
//        ){
//            item {
//                CoinDetailsRow(
//                    title = "Rank",
//                    value = "${coin.market_cap_rank}"
//                )
//            }
//            item {
//                CoinDetailsRow(
//                    title = "Price",
//                    value = "$${coin.current_price}"
//                )
//            }
//            item {
//                CoinDetailsRow(
//                    title = "24h price change",
//                    value = "$${coin.price_change_24h}"
//                )
//            }
//            item {
//                CoinDetailsRow(
//                    title = "24h High",
//                    value = "$${coin.high_24h}"
//                )
//            }
//            item {
//                CoinDetailsRow(
//                    title = "24h Low",
//                    value = "$${coin.low_24h}"
//                )
//            }
//            item {
//                CoinDetailsRow(
//                    title = "Market Cap",
//                    value = "$${coin.market_cap}"
//                )
//
//            }
//            item {
//                CoinDetailsRow(
//                    title = "Ath",
//                    value = "$${coin.ath}}"
//                )
//
//            }
//        }
//
//    }
//}
//@SuppressLint("ModifierParameter")
//@Composable
//fun CoinDetailsRow(
//    title: String,
//    value: String,
//    titleStyle: TextStyle = MaterialTheme.typography.bodySmall,
//    valueStyle: TextStyle = MaterialTheme.typography.bodySmall,
//    modifier: Modifier = Modifier
//){
//    Row(
//        modifier = modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween,
//    ) {
//        Text(
//            text = title,
//            style = titleStyle,
//        )
//
//        Text(
//            text = value,
//            style = valueStyle,
//        )
//    }
//
//
//}
