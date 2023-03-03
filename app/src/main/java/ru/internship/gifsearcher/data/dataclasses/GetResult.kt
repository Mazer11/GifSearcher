package ru.internship.gifsearcher.data.dataclasses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetResult(
    @Json(name = "data") val data: List<GifData>,
)

/**Data of single Gif*/
data class GifData(
    /**This GIF's unique ID*/
    val id: String,
    /**The unique URL for this GIF*/
    val url: String,
    /**The username this GIF is attached to, if applicable*/
    val username: String,
    /**The date this GIF was added to the GIPHY database.*/
    val create_datetime: String,
    /**The title that appears on giphy.com for this GIF.*/
    val title: String
)