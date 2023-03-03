package ru.internship.gifsearcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.internship.gifsearcher.data.remote.GifRepository
import ru.internship.gifsearcher.ui.navigation.NavigationGraph
import ru.internship.gifsearcher.ui.theme.GifSearcherTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: GifRepository

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GifSearcherTheme {
                val navController = rememberAnimatedNavController()

                NavigationGraph(navController = navController)
            }
        }
    }
}