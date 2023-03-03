package ru.internship.gifsearcher.ui.screens.details_screen

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.internship.gifsearcher.data.dataclasses.GifData
import ru.internship.gifsearcher.data.dataclasses.GifParcelable
import ru.internship.gifsearcher.data.dataclasses.Image
import ru.internship.gifsearcher.data.dataclasses.Original
import ru.internship.gifsearcher.ui.common.GifView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController
) {
    val passedData =
        navController.previousBackStackEntry?.savedStateHandle?.get<GifParcelable>(key = "gif")
    val gifData = GifData(
        username = passedData?.username ?: "",
        import_datetime = passedData?.import_datetime ?: "",
        title = passedData?.title ?: "",
        source = passedData?.source ?: "",
        image = Image(
            Original(
                passedData?.height ?: "",
                passedData?.size ?: "",
                passedData?.url ?: "",
                passedData?.width ?: ""
            )
        )
    )

    if (gifData.image.original.size.isNotEmpty())
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                ) {
                    Text(text = gifData.title)

                    GifView(
                        data = gifData,
                        contentScale = ContentScale.FillBounds,
                        clickEnabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((LocalConfiguration.current.screenHeightDp / 2).dp)
                            .padding(all = 8.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = "Created by ${gifData.username.ifEmpty { "Unknown" }}\n" +
                                "${gifData.import_datetime}\n" +
                                "Original sizes: ${gifData.image.original.width}" +
                                "x${gifData.image.original.height}\n" +
                                "Weight: ${
                                    (gifData.image.original.size.toFloat() / 1024 / 1024)
                                        .times(100).toInt().toFloat() / 100
                                }Mb"
                    )
                }

            }
        }
}
































