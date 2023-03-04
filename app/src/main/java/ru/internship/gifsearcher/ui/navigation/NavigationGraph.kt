package ru.internship.gifsearcher.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import ru.internship.gifsearcher.ui.screens.details_screen.DetailsScreen
import ru.internship.gifsearcher.ui.screens.main_screen.MainScreen
import ru.internship.gifsearcher.vm.MainViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
) {
    val startDestination = NavRoutes.MAIN.route

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(
            route = NavRoutes.MAIN.route,
        ) {
            val vm = hiltViewModel<MainViewModel>()
            MainScreen(vm = vm, navController = navController)
        }

        composable(
            route = NavRoutes.DETAILS.route,
            enterTransition = {
                fadeIn()
            },
            exitTransition = {
                fadeOut()
            }
        ) {
            DetailsScreen(navController)
        }

    }

}