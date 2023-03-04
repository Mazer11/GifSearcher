package ru.internship.gifsearcher.data.remote

import retrofit2.Call
import ru.internship.gifsearcher.data.common.Constants
import ru.internship.gifsearcher.data.dataclasses.GifsData

class GifRepository(
    private val api: GifApi
) {

    fun getTrendingGifs(
        limit: Int = 25,
        offset: Int = 0
    ): Call<GifsData> {
        return api.getTrendingGifs(
            api_key = Constants.api_key,
            limit = limit,
            offset = offset
        )
    }

    fun getGifsByName(value: String, limit: Int = 25, offset: Int = 0): Call<GifsData> {
        return api.getGifsByName(
            api_key = Constants.api_key,
            value = value,
            limit = limit,
            offset = offset
        )
    }

}