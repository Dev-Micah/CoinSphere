package com.micahnyabuto.coinsphere.ui.screens.market


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.micahnyabuto.coinsphere.R
import com.micahnyabuto.coinsphere.domain.model.Coin
import com.micahnyabuto.coinsphere.ui.navigation.Destinations
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import kotlin.math.abs


@Composable
fun MarketScreen(
    modifier: Modifier = Modifier,
    viewModel: MarketViewModel = koinViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val marketUiState by viewModel.marketUiState.collectAsState()
    var wasLoading by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = marketUiState is MarketUiState.Loading && wasLoading
    )
    LaunchedEffect(marketUiState) {
        when {
            wasLoading && marketUiState is MarketUiState.Success -> {
                Toast.makeText(context, "Refreshed successfully", Toast.LENGTH_SHORT).show()
            }
            marketUiState is MarketUiState.Error -> {
                Toast.makeText(context, "Failed to get data", Toast.LENGTH_SHORT).show()
            }
        }
        wasLoading = marketUiState is MarketUiState.Loading
    }
    LaunchedEffect(Unit) {
        delay(1000)
        viewModel.fetchCoins()
    }
    Scaffold(
        topBar = { TopAppABar() }
    ) { innerPadding ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.fetchCoins() },
            modifier = Modifier.padding(innerPadding)
        ) {
            when (marketUiState) {
                is MarketUiState.Loading -> {
                    MarketShimmerList()
                }
                is MarketUiState.Success -> {
                    MarketScreenContent(
                        coins = (marketUiState as MarketUiState.Success).coins,
                        navController = navController,
                    )
                }
                is MarketUiState.Error -> {
                    ErrorStateContent(
                        onRetry = { viewModel.fetchCoins() }
                    )
                }
            }
        }
    }
}


@Composable
private fun ErrorStateContent(onRetry: () -> Unit) {
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
            onClick = onRetry,
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Try Again")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreenContent(
    modifier: Modifier = Modifier,
    coins: List<Coin>,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier.padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(coins, key = { it.id }) { coin ->
            CoinsRow(
                coin = coin,
                onClick = {
                    navController.navigate(Destinations.Details.detailsRoute(coin.name))
                }
            )
            HorizontalDivider()
        }
    }
}


@Composable
fun CoinsRow(
    modifier: Modifier = Modifier,
    coin: Coin,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = coin.marketCapRank.toString(),
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(Modifier.width(16.dp))
        Image(
            painter = rememberAsyncImagePainter(coin.image),
            contentDescription = coin.name,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = coin.symbol.uppercase(),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = formatMarketCap(coin.marketCap),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$${coin.currentPrice}",
                style = MaterialTheme.typography.titleSmall,
            )
            val isPositive = coin.priceChangePercentage24h >= 0
            Row {
                Text(
                    text = if (isPositive) "▲ " else "▼ ",
                    color = if (isPositive) Color.Green else Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${String.format("%.2f", abs(coin.priceChangePercentage24h))}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isPositive) Color.Green else Color.Red
                )
            }
        }
    }
}

private fun formatMarketCap(marketCap: Long): String {
    val value = when {
        marketCap >= 1_000_000_000_000 -> Pair(marketCap / 1_000_000_000_000.0, "T")
        marketCap >= 1_000_000_000 -> Pair(marketCap / 1_000_000_000.0, "B")
        marketCap >= 1_000_000 -> Pair(marketCap / 1_000_000.0, "M")
        else -> return "$${marketCap}"
    }
    return "$${String.format("%.2f", value.first)}${value.second}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppABar() {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.splash),
                    contentDescription = "app logo",
                )
                Text(
                    "CoinSphere",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        actions = {
            // Profile icon
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.prof),
                contentDescription = "Profile",
                contentScale = ContentScale.Crop
            )
        }
    )
}

/**
 * Loading state with shimmer effect for placeholder items.
 */
@Composable
fun MarketShimmerList() {
    LazyColumn {
        items(10) {
            CoinShimmerRow()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun CoinShimmerRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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


        Column(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
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

        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )
    }
}
