package com.micahnyabuto.coinsphere.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    object Market

    @Serializable
    object Search

    @Serializable
    object Discover

    @Serializable
    object Favourite

    @Serializable
    object Settings
}