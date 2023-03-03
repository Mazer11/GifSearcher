package ru.internship.gifsearcher.data.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GifParcelable(
    /**This GIF's unique ID*/
    val id: String,
    /**The username this GIF is attached to, if applicable*/
    val username: String,
    /**The date this GIF was added to the GIPHY database.*/
    val import_datetime: String,
    /**The title that appears on giphy.com for this GIF.*/
    val title: String,
    /**Gif source.*/
    val source: String,
    val height: String,
    val size: String,
    val url: String,
    val width: String
) : Parcelable
