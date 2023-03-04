package ru.internship.gifsearcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.internship.gifsearcher.data.local.DataStoreRepository
import ru.internship.gifsearcher.ui.navigation.NavigationGraph
import ru.internship.gifsearcher.ui.theme.GifSearcherTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var application: GifApp

    @Inject
    lateinit var datastore: DataStoreRepository

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val themeValue = datastore.getThemeValue.collectAsState(initial = true)
            application.setAppTheme(themeValue.value)

            GifSearcherTheme(
                darkTheme = application.isDarkTheme
            ) {
                val navController = rememberAnimatedNavController()

                NavigationGraph(navController = navController)
            }
        }
    }
}