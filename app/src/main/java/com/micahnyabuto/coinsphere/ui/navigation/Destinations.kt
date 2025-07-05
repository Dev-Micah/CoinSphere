package com.micahnyabuto.coinsphere.ui.navigation

import kotlinx.serialization.Serializable
import okhttp3.Route

sealed class Destinations (val route: String){

    object Splash: Destinations("splash")
    object Market: Destinations("market")
    object SignUp: Destinations("signup")
    object SignIn: Destinations("signin")
    object Search: Destinations("search")
    object News: Destinations("news")
    object Favourite: Destinations("favourites")
    object Settings: Destinations("settings")
    object Details: Destinations("")

    object Profile: Destinations("profile")

}