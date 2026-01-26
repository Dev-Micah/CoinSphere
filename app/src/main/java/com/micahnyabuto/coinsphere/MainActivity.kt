package com.micahnyabuto.coinsphere

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.micahnyabuto.coinsphere.ui.navigation.AppNavHost
import com.micahnyabuto.coinsphere.ui.screens.settings.SettingsViewModel
import com.micahnyabuto.coinsphere.ui.theme.CoinSphereTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SettingsViewModel by viewModel()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsState()

            CoinSphereTheme (darkTheme = isDarkMode){

                AppNavHost()

            }
        }
    }
}
