package ru.internship.gifsearcher.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.internship.gifsearcher.GifApp
import ru.internship.gifsearcher.data.dataclasses.GiffsData
import ru.internship.gifsearcher.data.local.DataStoreRepository
import ru.internship.gifsearcher.data.remote.GifRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GifRepository,
    private val application: GifApp,
    private val datastore: DataStoreRepository
) : ViewModel() {

    private val _gifData: MutableLiveData<GiffsData> by lazy {
        MutableLiveData<GiffsData>()
    }
    val gifdata: LiveData<GiffsData> = _gifData

    private val _isLoadingState: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val isLoadingState: LiveData<Boolean> = _isLoadingState

    private val _isPageLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isPageLoading: LiveData<Boolean> = _isPageLoading

    private val _isDarkTheme: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    private val _loadingFailed: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val loadingFailed: LiveData<Boolean> = _loadingFailed

    private val _tagText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val tagText: LiveData<String> = _tagText
    var currentPage = 25
    private var isNewCardsRequested = false

    init {
        _isLoadingState.value = true
        viewModelScope.launch {
            getTrendingGifs(
                onLoadSuccess = { _isLoadingState.value = false },
                onLoadFailure = {
                    _loadingFailed.value = true
                    _isLoadingState.value = false
                })
        }
    }

    fun switchPageLoadingIndicator() {
        _isPageLoading.value = _isPageLoading.value?.not()
    }

    fun loadNextTrendingGifsPage() {
        if (!isNewCardsRequested)
            viewModelScope.launch {
                isNewCardsRequested = true
                getNewTrendingGifs(
                    onLoadSuccess = {
                        isNewCardsRequested = false
                    }
                )
            }
    }

    fun onSearch(
        value: String,
        onLoadSuccess: () -> Unit = {},
        onLoadFailure: () -> Unit = {}
    ) {
        _tagText.value = value
        currentPage = 0
        _isPageLoading.value = false
        _loadingFailed.value = false
        isNewCardsRequested = false

        viewModelScope.launch {
            if (value.isNotEmpty())
                searchGifs(
                    value = value,
                    onLoadSuccess = onLoadSuccess,
                    onLoadFailure = onLoadFailure
                )
            else
                getTrendingGifs(
                    onLoadFailure = {
                        _isLoadingState.value = false
                    }
                )
        }
    }

    fun loadNextSearchedGifsPage(
        value: String
    ) {
        if (!isNewCardsRequested)
            viewModelScope.launch {
                isNewCardsRequested = true
                getNewSearchedGifs(
                    value = value,
                    onLoadSuccess = {
                        isNewCardsRequested = false
                    }
                )
            }
    }

    fun switchAppTheme() {
        application.switchAppTheme()
        _isDarkTheme.value = application.isDarkTheme
        viewModelScope.launch {
            datastore.switchThemePreference()
        }
    }

    fun retryLoading() {
        _isLoadingState.value = true
        viewModelScope.launch {
            delay(500)
            getTrendingGifs(
                onLoadSuccess = {
                    _isLoadingState.value = false
                    _loadingFailed.value = false
                },
                onLoadFailure = {
                    _loadingFailed.value = true
                    _isLoadingState.value = false
                }
            )
        }
    }

    fun getAppTheme(): Boolean {
        return application.isDarkTheme
    }

    private suspend fun getNewTrendingGifs(
        onLoadSuccess: () -> Unit = {},
        onLoadFailure: () -> Unit = {}
    ) = withContext(Dispatchers.IO) {
        repository.getSomeNewGiffs(offset = if (currentPage < 25) 0 else currentPage)
            .enqueue(object : Callback<GiffsData> {
                override fun onResponse(call: Call<GiffsData>, response: Response<GiffsData>) {
                    _gifData.value = _gifData.value?.copy(
                        data = buildList {
                            addAll(_gifData.value!!.data)
                            addAll(response.body()!!.data)
                        }
                    )
                    currentPage += 25
                    onLoadSuccess()
                }

                override fun onFailure(call: Call<GiffsData>, t: Throwable) {
                    onLoadFailure()
                    Log.e("FAILURE GET", t.message.toString())
                }

            })
    }

    private suspend fun getNewSearchedGifs(
        value: String,
        onLoadSuccess: () -> Unit = {},
        onLoadFailure: () -> Unit = {}
    ) = withContext(Dispatchers.IO) {
        Log.e("SearchNEW", "Search new gifs with offset $currentPage")
        repository.getGiffsByName(
            value = value,
            offset = if (currentPage < 25) 0 else currentPage
        )
            .enqueue(object : Callback<GiffsData> {
                override fun onResponse(call: Call<GiffsData>, response: Response<GiffsData>) {
                    _gifData.value = _gifData.value?.copy(
                        data = buildList {
                            addAll(_gifData.value!!.data)
                            addAll(response.body()!!.data)
                        }
                    )
                    currentPage += 25
                    onLoadSuccess()
                }

                override fun onFailure(call: Call<GiffsData>, t: Throwable) {
                    onLoadFailure()
                    Log.e("FAILURE GET", t.message.toString())
                }

            })
    }

    private suspend fun getTrendingGifs(
        onLoadSuccess: () -> Unit = {},
        onLoadFailure: () -> Unit = {}
    ) = withContext(Dispatchers.IO) {
        repository.getSomeNewGiffs().enqueue(object : Callback<GiffsData> {
            override fun onResponse(call: Call<GiffsData>, response: Response<GiffsData>) {
                _gifData.value = response.body()
                onLoadSuccess()
            }

            override fun onFailure(call: Call<GiffsData>, t: Throwable) {
                onLoadFailure()
                Log.e("FAILURE GET", t.message.toString())
            }

        })
    }

    private suspend fun searchGifs(
        value: String,
        onLoadSuccess: () -> Unit = {},
        onLoadFailure: () -> Unit = {}
    ) = withContext(Dispatchers.IO) {
        Log.e("SearchFirst", "FIRST search start. currentPage = $currentPage")
        repository.getGiffsByName(
            value = value,
            offset = 0
        ).enqueue(object : Callback<GiffsData> {
            override fun onResponse(call: Call<GiffsData>, response: Response<GiffsData>) {

                _gifData.value = response.body()
                currentPage += 25
                onLoadSuccess()
                Log.e("SearchFirst", "FIRST search end. currentPage = $currentPage")
            }

            override fun onFailure(call: Call<GiffsData>, t: Throwable) {
                Log.e("SearchGifs", "Failed to search gifs throws: ${t.message}")
                onLoadFailure()
            }

        })
    }

}