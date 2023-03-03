package ru.internship.gifsearcher.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.internship.gifsearcher.data.dataclasses.GiffsData

interface GifApi {

    @GET("/tranding?api_key={api_key}&limit={limit}")
    fun getSomeNewGiffs(
        @Path("api_key") api_key: String,
        @Path("limit") limit: Int = 25
    ): Call<GiffsData>

    @GET("/search?api_key={api_key}&q={value}")
    fun getGiffsByName(
        @Path("api_key") api_key: String,
        @Path("value") value: String
    ): Call<GiffsData>

}