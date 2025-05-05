package com.micahnyabuto.coinsphere.ui.screens.market


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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.micahnyabuto.coinsphere.R
import com.micahnyabuto.coinsphere.data.remote.Coin

@Composable
fun MarketScreen(
    modifier: Modifier=Modifier,
    viewModel: MarketViewModel =hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.fetchCoins()
    }
    when (uiState) {
        is UiState.Loading -> {
            MarketShimmerList()
        }
        is UiState.Success -> {
            MarketScreenContent(coins = (uiState as UiState.Success).data)
        }
        is UiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
            Text("Something went wrong",
                style = MaterialTheme.typography.bodyMedium
                )
                Button(
                    onClick = {viewModel.fetchCoins()}
                ) {
                    Text("Refresh again")
                }
            }
        }
    }


}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreenContent(
    modifier: Modifier= Modifier,
    coins: List<Coin>
){


    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("CoinSphere",
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.titleLarge
                        )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",

                    )
                },
                navigationIcon = {
                    Image(
                        modifier= Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            ,
                        painter = painterResource(id =R.drawable.prof),
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop

                    )

                }
            )
        }
    ) { innerpadding ->
                LazyColumn(
                    modifier = Modifier.padding(innerpadding),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(coins) { coin ->
                        CoinsRow(coin = coin)
                        HorizontalDivider()


                    }
                }
            }



    }
@Composable
fun CoinsRow(
    modifier: Modifier= Modifier,
    coin: Coin
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = rememberAsyncImagePainter(coin.image),
            contentDescription = coin.name,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Row {
            Text(
                text = "${coin.symbol}", style = MaterialTheme.typography.bodyMedium
            )
            Text(text = "${coin.current_price}")

            Text(
                text = "24h Change: ${String.format("%.2f", coin.price_change_percentage_24h)}%",
                color = if (coin.price_change_percentage_24h >= 0)
                    Color.Green else Color.Red
            )
        }

    }
}

@Composable
fun CoinShimmerRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
        }
    }
}
@Composable
fun MarketShimmerList() {
    LazyColumn {
        items(100) { // Show 100 shimmer rows
            CoinShimmerRow()
        }
    }
}


