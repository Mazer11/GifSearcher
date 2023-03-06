package ru.internship.gifsearcher.ui.screens.details_screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.internship.gifsearcher.R
import ru.internship.gifsearcher.data.dataclasses.GifData
import ru.internship.gifsearcher.data.dataclasses.GifParcelable
import ru.internship.gifsearcher.data.dataclasses.Image
import ru.internship.gifsearcher.data.dataclasses.Original
import ru.internship.gifsearcher.ui.common.GifView

/**The second screen of contest. Displays one Gif and some info.*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController
) {
    /**Tapped Gif data.*/
    val passedData =
        navController.previousBackStackEntry?.savedStateHandle?.get<GifParcelable>(key = "gif")
    val context = LocalContext.current
    val isError = remember { mutableStateOf(false) }
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

            if (isError.value) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    ) {
                        Text(
                            text = stringResource(R.string.failed_load),
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { isError.value = false },
                        ) {
                            Text(text = stringResource(R.string.retry))
                        }
                    }
                }

            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                ) {
                    GifView(
                        data = gifData.image.original.url,
                        clickEnabled = false,
                        onError = {
                            isError.value = true
                            Toast.makeText(context, "Loading error", Toast.LENGTH_LONG).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((LocalConfiguration.current.screenHeightDp / 2).dp)
                            .padding(horizontal = 8.dp, vertical = 26.dp)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            text = gifData.title,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                        )

                        Text(
                            text = stringResource(R.string.created_by) +
                                    gifData.username.ifEmpty { stringResource(R.string.unknown) } +
                                    "\n" + gifData.import_datetime + "\n" +
                                    stringResource(R.string.original_sizes) +
                                    gifData.image.original.width + " " + "x" +
                                    gifData.image.original.height + "\n" +
                                    stringResource(R.string.weight) +
                                    (gifData.image.original.size.toFloat() / 1024 / 1024)
                                        .times(100).toInt().toFloat() / 100
                                    + stringResource(R.string.mb),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                        )
                    }

                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.back),
                        )
                    }
                }
            }
        }
}