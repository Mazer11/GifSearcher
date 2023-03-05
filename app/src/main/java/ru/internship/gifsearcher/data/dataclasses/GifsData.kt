package ru.internship.gifsearcher.data.dataclasses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**List of Gifs*/
@JsonClass(generateAdapter = true)
data class GifsData(
    @Json(name = "data") var data: List<GifData>,
)