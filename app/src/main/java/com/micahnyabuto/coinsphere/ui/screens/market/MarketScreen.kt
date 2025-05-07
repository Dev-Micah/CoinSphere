package com.micahnyabuto.coinsphere.ui.screens.market


import android.annotation.SuppressLint
import android.util.Log.i
import android.widget.Toast
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.micahnyabuto.coinsphere.R
import com.micahnyabuto.coinsphere.data.remote.Coin

/**
 * The market screen.
 */
@Composable
fun MarketScreen(
    modifier: Modifier=Modifier,
    viewModel: MarketViewModel =hiltViewModel()
){
    val context =LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var wasLoading by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if (wasLoading && uiState is UiState.Success) {
            Toast.makeText(context, "Refreshed successfully", Toast.LENGTH_SHORT).show()
        }
        wasLoading = uiState is UiState.Loading
        if (uiState is UiState.Error) {
            Toast.makeText(context, "Failed to refresh data 🥲 Check internet connection and try again", Toast.LENGTH_SHORT).show()
        }
    }



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
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text("Something went wrong!",
                style = MaterialTheme.typography.titleLarge.copy()
                )
                Spacer(Modifier.height(25.dp))
                Button(
                    onClick = {viewModel.fetchCoins()},
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Refresh again")
                }
            }
        }
    }


}

/**
 * The content of the market screen.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreenContent(
    modifier: Modifier= Modifier,
    coins: List<Coin>,
    viewModel: MarketViewModel =hiltViewModel()
){
  /*
  * Swipe refresh state for the market screen.
   */
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

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
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "notifications",

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

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.fetchCoins() },
        )
        {
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



    }

/**
 * A row for the market screen.
 */
@SuppressLint("DefaultLocale")
@Composable
fun CoinsRow(
    modifier: Modifier= Modifier,
    coin: Coin
) {
    Row(
        modifier = Modifier.fillMaxSize()
            .padding(start = 8.dp),
    ) {

        Image(
            painter = rememberAsyncImagePainter(coin.image),
            contentDescription = coin.name,
            modifier = Modifier.size(35.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(32.dp))
        Row {
            Text(
                text = "${coin.symbol.uppercase()}", style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.width(40.dp))

        Row {
            Text(text = "$${coin.current_price}", style = MaterialTheme.typography.bodyMedium)
          }
            Spacer(modifier = Modifier.width(40.dp))
        Row {
            Text(
                text = " ${String.format("%.2f", coin.price_change_percentage_24h)}%",
                style = MaterialTheme.typography.bodyMedium,
                color = if (coin.price_change_percentage_24h >= 0)
                    Color.Green else Color.Red
            )
        }

    }
}

/**
 *A shimmer row for the market screen.
 */
@Composable
fun CoinShimmerRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 70.dp),
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
                    .fillMaxWidth()
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
                    .fillMaxWidth()
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
        items(150) { // Show 100 shimmer rows
            CoinShimmerRow()
        }
    }
}


