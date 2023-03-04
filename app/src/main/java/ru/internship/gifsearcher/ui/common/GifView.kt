package ru.internship.gifsearcher.ui.common

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import ru.internship.gifsearcher.data.dataclasses.GifData

@Composable
fun GifView(
    data: GifData,
    contentScale: ContentScale? = null,
    clickEnabled: Boolean,
    modifier: Modifier,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val gifSize = ((LocalConfiguration.current.screenWidthDp - 32) / 3).dp
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()

    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(data.image.original.url)
//                .placeholder()
                .crossfade(true).build(),
            imageLoader = imageLoader,
            contentScale = contentScale ?: ContentScale.Fit
        ),
        contentScale = contentScale ?: ContentScale.Fit ,
        contentDescription = "",
        modifier = modifier
            .size(gifSize)
            .clickable(enabled = clickEnabled) { onClick() }
    )
}