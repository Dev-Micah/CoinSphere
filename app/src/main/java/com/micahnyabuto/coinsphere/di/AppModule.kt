package com.micahnyabuto.coinsphere.di

import com.micahnyabuto.coinsphere.ui.screens.details.CoinDetailsViewModel
import com.micahnyabuto.coinsphere.ui.screens.favourite.FavouritesViewModel
import com.micahnyabuto.coinsphere.ui.screens.market.MarketViewModel
import com.micahnyabuto.coinsphere.ui.screens.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MarketViewModel(get()) }
    viewModel { FavouritesViewModel() }
    viewModel { CoinDetailsViewModel(get()) }
    viewModel { SettingsViewModel(get()) }

}