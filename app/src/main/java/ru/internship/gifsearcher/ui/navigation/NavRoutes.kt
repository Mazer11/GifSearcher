package ru.internship.gifsearcher.ui.navigation

sealed class NavRoutes(val route: String) {
    object MAIN : NavRoutes("main")
    object DETAILS : NavRoutes("details")
}
