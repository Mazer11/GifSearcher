package ru.internship.gifsearcher.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.internship.gifsearcher.GifApp
import ru.internship.gifsearcher.data.local.DataStoreRepository
import ru.internship.gifsearcher.data.remote.GifApi
import ru.internship.gifsearcher.data.remote.GifRepository
import ru.internship.gifsearcher.data.usecases.GetGifsByName
import ru.internship.gifsearcher.data.usecases.GetTrendingNewGifs
import ru.internship.gifsearcher.data.usecases.RemoteUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApp(@ApplicationContext app: Context) = app as GifApp

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.giphy.com/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideGifApi(retrofit: Retrofit): GifApi =
        retrofit.create(GifApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofitRepository(api: GifApi): GifRepository {
        return GifRepository(api)
    }

    @Provides
    @Singleton
    fun provideDataStore(app: GifApp): DataStoreRepository {
        return DataStoreRepository(app)
    }

    @Provides
    @Singleton
    fun providesRemoteUseCases(repository: GifRepository): RemoteUseCases {
        return RemoteUseCases(
            getGifsByName = GetGifsByName(repository),
            getTrendingNewGifs = GetTrendingNewGifs(repository)
        )
    }

}