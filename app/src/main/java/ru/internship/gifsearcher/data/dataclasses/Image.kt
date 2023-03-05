package ru.internship.gifsearcher.data.dataclasses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**Stores [Original] instance with Gif url.*/
@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "original") val original: Original
)