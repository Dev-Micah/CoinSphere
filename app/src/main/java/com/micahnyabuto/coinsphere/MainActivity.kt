package com.micahnyabuto.coinsphere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.micahnyabuto.coinsphere.ui.screens.market.MarketScreen
import com.micahnyabuto.coinsphere.ui.screens.market.MarketScreenContent
import com.micahnyabuto.coinsphere.ui.screens.market.MarketViewModel
import com.micahnyabuto.coinsphere.ui.theme.CoinSphereTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val viewModel: MarketViewModel = hiltViewModel()
            CoinSphereTheme {
                MarketScreen(
                    modifier =Modifier,
                    viewModel = viewModel
                )
            }
        }
    }
}

