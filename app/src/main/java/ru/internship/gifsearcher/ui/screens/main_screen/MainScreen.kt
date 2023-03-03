package ru.internship.gifsearcher.ui.screens.main_screen

import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import ru.internship.gifsearcher.ui.common.LoadingScreen
import ru.internship.gifsearcher.vm.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainViewModel,
    navController: NavController
) {

    val isLoading = vm.isLoadingState.observeAsState(true)
    val context = LocalContext.current


    Scaffold { paddingValues ->
        if (isLoading.value) {

            LoadingScreen(paddingValues)

        } else {

            val giffsData = vm.gifdata.observeAsState()
            val imageLoader = ImageLoader.Builder(context)
                .components {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }.build()

            Log.e(
                "urla", "Size is ${giffsData.value?.data?.size.toString()}\n"
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(text = giffsData.value?.data?.first()?.image?.original?.url.toString())

                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .data(giffsData.value?.data?.first()?.image?.original?.url.toString())
                            .crossfade(true).build(),
                        imageLoader = imageLoader,
                        contentScale = ContentScale.FillBounds
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                )
            }
        }
    }
}