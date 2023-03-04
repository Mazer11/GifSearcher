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
import androidx.compose.ui.text.style.TextAlign
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
            ) {
                GifView(
                    data = gifData,
                    clickEnabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((LocalConfiguration.current.screenHeightDp / 2).dp)
                        .padding(horizontal = 8.dp, vertical = 26.dp)
                )

                Text(text = gifData.title)

                Text(
                    text = "Created by ${gifData.username.ifEmpty { "Unknown" }}\n" +
                            "${gifData.import_datetime}\n" +
                            "Original sizes: ${gifData.image.original.width}" +
                            "x${gifData.image.original.height}\n" +
                            "Weight: ${
                                (gifData.image.original.size.toFloat() / 1024 / 1024)
                                    .times(100).toInt().toFloat() / 100
                            }Mb",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                )
            }
        }
}

































