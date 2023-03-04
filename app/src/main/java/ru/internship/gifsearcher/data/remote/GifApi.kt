package ru.internship.gifsearcher.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.internship.gifsearcher.data.dataclasses.GiffsData

interface GifApi {

    @GET("v1/gifs/trending")
    fun getSomeNewGiffs(
        @Query("api_key") api_key: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<GiffsData>

    @GET("v1/gifs/search")
    fun getGiffsByName(
        @Query("api_key") api_key: String,
        @Query("q") value: String,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int
    ): Call<GiffsData>

}