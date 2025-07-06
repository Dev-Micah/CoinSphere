package com.micahnyabuto.coinsphere.ui.navigation

sealed class Destinations (val route: String){

    object Splash: Destinations("splash")
    object Market: Destinations("market")
    object SignUp: Destinations("signup")
    object SignIn: Destinations("login")
    object Search: Destinations("search")
    object News: Destinations("news")
    object Favourite: Destinations("favourites")
    object Settings: Destinations("settings")
    object Details: Destinations("coinDetails/{coinName}")

    object Profile: Destinations("profile")

    fun detailsRoute (coinName: String) = "coinDetails/$coinName"

}