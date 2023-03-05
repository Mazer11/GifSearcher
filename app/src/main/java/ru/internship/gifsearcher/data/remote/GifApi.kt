package ru.internship.gifsearcher.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.internship.gifsearcher.data.dataclasses.GifsData

interface GifApi {

    /**Get query that returns [GifsData] of 25 trending gifs.
     * @param api_key GIPHY API Key.
     * @param limit The maximum number of objects to return.
     * @param offset Specifies the starting position of the results.
     * */
    @GET("v1/gifs/trending")
    fun getTrendingGifs(
        @Query("api_key") api_key: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<GifsData>

    /**Get query that returns relevant to search value [GifsData].
     * @param api_key GIPHY API Key.
     * @param value Search query term or phrase.
     * @param limit The maximum number of objects to return.
     * @param offset Specifies the starting position of the results.
     * */
    @GET("v1/gifs/search")
    fun getGifsByName(
        @Query("api_key") api_key: String,
        @Query("q") value: String,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int
    ): Call<GifsData>

}