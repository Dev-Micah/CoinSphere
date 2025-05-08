package com.micahnyabuto.coinsphere.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(
    modifier: Modifier= Modifier,
    navController: NavController
){
    var isDarkMode by remember { mutableStateOf(false) }
    var isDarkModeToggle by remember { mutableStateOf(false) }

    SettingsScreenContent(
        modifier = modifier,
        isDarkMode = isDarkMode,
        isDarkModeToggle = isDarkModeToggle,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    isDarkModeToggle: Boolean,
){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Settings",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ) )
                }
            )
        }
    ){ PaddingValues ->
        Column (
            modifier = Modifier
                .padding(PaddingValues)
                .padding(8.dp)
                .fillMaxSize()
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Enable DarkTheme")

                Switch(
                    checked = isDarkMode,
                    onCheckedChange = {isDarkModeToggle}
                )


            }
            Spacer(Modifier.height(2.dp))
            HorizontalDivider()
        }

    }



}