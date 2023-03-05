package ru.internship.gifsearcher.data.remote

import retrofit2.Call
import ru.internship.gifsearcher.data.common.Constants
import ru.internship.gifsearcher.data.dataclasses.GifsData

/**Get requests of API*/
class GifRepository(
    private val api: GifApi
) {

    /**Get query that returns [GifsData] of 25 trending gifs.
     * @param limit The maximum number of objects to return.
     * @param offset Specifies the starting position of the results.
     * */
    fun getTrendingGifs(limit: Int = 25, offset: Int = 0): Call<GifsData> {
        return api.getTrendingGifs(
            api_key = Constants.api_key,
            limit = limit,
            offset = offset
        )
    }

    /**Get query that returns relevant to search value [GifsData].
     * @param value Search query term or phrase.
     * @param limit The maximum number of objects to return.
     * @param offset Specifies the starting position of the results.
     * */
    fun getGifsByName(value: String, limit: Int = 25, offset: Int = 0): Call<GifsData> {
        return api.getGifsByName(
            api_key = Constants.api_key,
            value = value,
            limit = limit,
            offset = offset
        )
    }

}