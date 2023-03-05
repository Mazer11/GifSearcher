package ru.internship.gifsearcher.data.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GifParcelable(
    /**The username this GIF is attached to, if applicable*/
    val username: String,
    /**The date this GIF was added to the GIPHY database.*/
    val import_datetime: String,
    /**The title that appears on giphy.com for this GIF.*/
    val title: String,
    /**Gif source.*/
    val source: String,
    /**Gif height.*/
    val height: String,
    /**Gif size in bytes.*/
    val size: String,
    /**Gif URL.*/
    val url: String,
    /**Gif width.*/
    val width: String
) : Parcelable
