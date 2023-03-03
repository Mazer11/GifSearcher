package ru.internship.gifsearcher.data.dataclasses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Original(
    @Json(name = "height")val height: String,
    @Json(name = "size")val size: String,
    @Json(name = "url")val url: String,
    @Json(name = "width")val width: String
)