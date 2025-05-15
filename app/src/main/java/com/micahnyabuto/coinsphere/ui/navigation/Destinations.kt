package com.micahnyabuto.coinsphere.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    object Splash
    @Serializable
    object Market

    @Serializable
    object SignUp

    @Serializable
    object SignIn


    @Serializable
    object Search

    @Serializable
    object News

    @Serializable
    object Favourite

    @Serializable
    object Settings

    @Serializable
    object Details

    @Serializable
    object Profile

}