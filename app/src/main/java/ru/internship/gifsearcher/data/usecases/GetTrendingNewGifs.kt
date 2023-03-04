package ru.internship.gifsearcher.data.usecases

import retrofit2.Call
import ru.internship.gifsearcher.data.dataclasses.GifsData
import ru.internship.gifsearcher.data.remote.GifRepository

class GetTrendingNewGifs(
    private val repository: GifRepository
) {

    operator fun invoke(
        offset: Int = 0
    ): Call<GifsData> {
        return repository.getTrendingGifs(
            offset = offset
        )
    }

}