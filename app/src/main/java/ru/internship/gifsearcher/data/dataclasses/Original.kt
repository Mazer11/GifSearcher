package ru.internship.gifsearcher.data.dataclasses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**This data class stores url of GIF and some info about it*/
@JsonClass(generateAdapter = true)
data class Original(
    /**Gif height.*/
    @Json(name = "height")val height: String,
    /**Gif size in bytes.*/
    @Json(name = "size")val size: String,
    /**Gif URL.*/
    @Json(name = "url")val url: String,
    /**Gif width.*/
    @Json(name = "width")val width: String
)