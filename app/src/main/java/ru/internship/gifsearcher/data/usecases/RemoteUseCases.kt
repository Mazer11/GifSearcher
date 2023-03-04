package ru.internship.gifsearcher.data.usecases

data class RemoteUseCases(
    val getGifsByName: GetGifsByName,
    val getTrendingNewGifs: GetTrendingNewGifs
)