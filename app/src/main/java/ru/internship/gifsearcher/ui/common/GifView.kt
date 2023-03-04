package ru.internship.gifsearcher.ui.common

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import ru.internship.gifsearcher.R
import ru.internship.gifsearcher.data.dataclasses.GifData

@Composable
fun GifView(
    data: GifData,
    contentScale: ContentScale? = null,
    clickEnabled: Boolean,
    modifier: Modifier,
    onLoadSuccess: () -> Unit = {},
    onLoading: () -> Unit = {},
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
        }.diskCachePolicy(CachePolicy.DISABLED).memoryCachePolicy(CachePolicy.DISABLED)
        .build()
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(data.image.original.url)
            .error(R.drawable.image_error)
            .crossfade(true).build(),
        imageLoader = imageLoader,
        onSuccess = {
            onLoadSuccess()
        },
        onLoading = {
            onLoading()
        },
        contentScale = contentScale ?: ContentScale.Fit
    )

    Image(
        painter = painter,
        contentScale = contentScale ?: ContentScale.Fit,
        contentDescription = "",
        modifier = modifier
            .size(gifSize)
            .clickable(enabled = clickEnabled) {
                if (painter.state is AsyncImagePainter.State.Success)
                    onClick()
            }
    )
    if (painter.state is AsyncImagePainter.State.Loading) {
        Box(modifier = Modifier.size(gifSize)) {
            CircularProgressIndicator(
                strokeWidth = 1.dp,
                modifier = Modifier
                    .size(gifSize.div(3))
                    .align(Alignment.Center)
            )
        }
    }
}