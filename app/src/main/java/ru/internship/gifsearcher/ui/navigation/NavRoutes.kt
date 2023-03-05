package ru.internship.gifsearcher.ui.navigation

/**Navigation routes of [NavigationGraph].*/
sealed class NavRoutes(val route: String) {
    object MAIN : NavRoutes("main")
    object DETAILS : NavRoutes("details")
}
