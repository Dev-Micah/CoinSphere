package com.micahnyabuto.coinsphere.ui.screens.details

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.micahnyabuto.coinsphere.domain.model.Coin
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinDetailsScreen(
    coinDetailsViewModel:CoinDetailsViewModel = koinViewModel(),
) {
    val coinUiState by coinDetailsViewModel.coinUiState.collectAsState()

    LaunchedEffect(Unit) {
        coinDetailsViewModel.getDetails()
    }

    when (coinUiState) {
        is CoinUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is CoinUiState.Success -> {
            CoinDetailsScreenContent(
                coins = (coinUiState as CoinUiState.Success).coin
            )
        }

        is CoinUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Something went wrong!",
                    style = MaterialTheme.typography.titleLarge.copy()
                )
                Spacer(Modifier.height(25.dp))
                Button(
                    onClick = { coinDetailsViewModel.getDetails() },
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Refresh again")
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CoinDetailsScreenContent(
    coins: List<Coin>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(coins) { coin ->
                CoinDetailsRow(coin = coin)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailsRow(
    coin: Coin
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberAsyncImagePainter(coin.image),
                        contentDescription = coin.name,
                        modifier = Modifier.size(35.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = coin.name.uppercase())
                }

                IconButton(onClick = { /* Handle favorite */ }) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "isFavourite"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CoinDetails("Rank", "${coin.marketCapRank}")
            CoinDetails("Price", "$${coin.currentPrice}")
            CoinDetails("24h price change", "$${coin.priceChange24h}")
            CoinDetails("24h High", "$${coin.high24h}")
            CoinDetails("24h Low", "$${coin.low24h}")
            CoinDetails("Market Cap", "$${coin.marketCap}")
            CoinDetails("Market cap change 24h", "$${coin.marketCapChange24h}")
            CoinDetails("Ath", "$${coin.ath}")

        }
    }
}

@SuppressLint("ModifierParameter")
@Composable
fun CoinDetails(
    title: String,
    value: String,
    titleStyle: TextStyle = MaterialTheme.typography.bodySmall,
    valueStyle: TextStyle = MaterialTheme.typography.bodySmall,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = titleStyle,
        )

        Text(
            text = value,
            style = valueStyle,
        )
    }
}
