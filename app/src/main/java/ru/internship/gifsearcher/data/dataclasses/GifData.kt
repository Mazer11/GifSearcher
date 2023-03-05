package ru.internship.gifsearcher.data.dataclasses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**Data of single Gif*/
@JsonClass(generateAdapter = true)
data class GifData(
    /**The username this GIF is attached to, if applicable*/
    @Json(name = "username") val username: String,
    /**The date this GIF was added to the GIPHY database.*/
    @Json(name = "import_datetime") val import_datetime: String,
    /**The title that appears on giphy.com for this GIF.*/
    @Json(name = "title") val title: String,
    /**Gif source.*/
    @Json(name = "source") val source: String,
    /**Object, that stores many Gif urls and info about it*/
    @Json(name = "images") val image: Image
)