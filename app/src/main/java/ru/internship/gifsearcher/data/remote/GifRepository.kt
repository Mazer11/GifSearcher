package ru.internship.gifsearcher.data.remote

import retrofit2.Call
import ru.internship.gifsearcher.data.common.Constants
import ru.internship.gifsearcher.data.dataclasses.GiffsData

class GifRepository(
    private val api: GifApi
) {

    fun getSomeNewGiffs(
        limit: Int = 25,
        offset: Int = 0
    ): Call<GiffsData> {
        return api.getSomeNewGiffs(
            api_key = Constants.api_key,
            limit = limit,
            offset = offset
        )
    }

    fun getGiffsByName(value: String, limit: Int = 25): Call<GiffsData> {
        return api.getGiffsByName(
            api_key = Constants.api_key,
            value = value,
            limit = limit
        )
    }

}