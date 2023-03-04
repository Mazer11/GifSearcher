package ru.internship.gifsearcher.data.usecases

import retrofit2.Call
import ru.internship.gifsearcher.data.dataclasses.GifsData
import ru.internship.gifsearcher.data.remote.GifRepository

class GetGifsByName(
    private val repository: GifRepository
) {

    operator fun invoke(value: String, offset: Int): Call<GifsData> {
        return repository.getGifsByName(
            value = value,
            offset = offset
        )
    }

}