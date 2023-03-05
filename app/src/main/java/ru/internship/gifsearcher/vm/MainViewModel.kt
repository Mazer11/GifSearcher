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
import ru.internship.gifsearcher.data.dataclasses.GifsData
import ru.internship.gifsearcher.data.local.DataStoreRepository
import ru.internship.gifsearcher.data.usecases.RemoteUseCases
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteUseCases: RemoteUseCases,
    private val application: GifApp,
    private val datastore: DataStoreRepository
) : ViewModel() {

    /**Current Gifs list.*/
    private val _gifData: MutableLiveData<GifsData> by lazy {
        MutableLiveData<GifsData>()
    }
    val gifData: LiveData<GifsData> = _gifData

    /**Starts LoadingScreen on MainScreen if true]*/
    private val _isLoadingState: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val isLoadingState: LiveData<Boolean> = _isLoadingState

    /**True if currently loading new page*/
    private val _isPageLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isPageLoading: LiveData<Boolean> = _isPageLoading

    /**Theme mode.*/
    private val _isDarkTheme: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    /**True if cannot load data from server.*/
    private val _loadingFailed: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val loadingFailed: LiveData<Boolean> = _loadingFailed

    /**Current query value. Shows "Trending" if search query not specified.*/
    private val _tagText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val tagText: LiveData<String> = _tagText

    /**Current page for pagination.*/
    var currentPage = 0

    /**True if new page is loading. Prevents wrong page loading.*/
    private var isNewCardsRequested = false

    init {
        _isLoadingState.value = true
        viewModelScope.launch {
            getNewTrendingGifs(
                onLoadSuccess = { _isLoadingState.value = false },
                onLoadFailure = {
                    _loadingFailed.value = true
                    _isLoadingState.value = false
                })
        }
    }

    /**Switches loading indicator of main screen (bottom) on or off.*/
    fun switchPageLoadingIndicator() {
        _isPageLoading.value = _isPageLoading.value?.not()
    }

    /**Loads new page of trending Gifs.*/
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

    /**Search event handler.*/
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
                getNewSearchedGifs(
                    value = value,
                    onLoadSuccess = onLoadSuccess,
                    onLoadFailure = onLoadFailure
                )
            else
                getNewTrendingGifs(
                    onLoadFailure = {
                        _isLoadingState.value = false
                    }
                )
        }
    }

    /**Loads additional pages for search request.*/
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

    /**Switches application theme mod.*/
    fun switchAppTheme() {
        application.switchAppTheme()
        _isDarkTheme.value = application.isDarkTheme
        viewModelScope.launch {
            datastore.switchThemePreference()
        }
    }

    /**Works when user clicks on retry button. It shows when failed to connect with server.*/
    fun retryLoading() {
        _isLoadingState.value = true
        viewModelScope.launch {
            delay(500)
            getNewTrendingGifs(
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

    /**Returns current app theme mod.*/
    fun getAppTheme(): Boolean {
        return application.isDarkTheme
    }

    /**Sends get request for new page with popular gifs.*/
    private suspend fun getNewTrendingGifs(
        onLoadSuccess: () -> Unit = {},
        onLoadFailure: () -> Unit = {}
    ) = withContext(Dispatchers.IO) {
        remoteUseCases.getTrendingNewGifs.invoke(offset = if (currentPage < 24) 0 else currentPage)
            .enqueue(object : Callback<GifsData> {
                override fun onResponse(call: Call<GifsData>, response: Response<GifsData>) {
                    if (currentPage == 0)
                        _gifData.value = response.body()
                    else
                        _gifData.value = _gifData.value?.copy(
                            data = buildList {
                                addAll(_gifData.value!!.data)
                                addAll(response.body()!!.data)
                            }
                        )
                    currentPage += 24
                    onLoadSuccess()
                }

                override fun onFailure(call: Call<GifsData>, t: Throwable) {
                    onLoadFailure()
                    Log.e("FAILURE GET", t.message.toString())
                }

            })
    }

    /**Sends get request for new page of search query.*/
    private suspend fun getNewSearchedGifs(
        value: String,
        onLoadSuccess: () -> Unit = {},
        onLoadFailure: () -> Unit = {}
    ) = withContext(Dispatchers.IO) {
        remoteUseCases.getGifsByName.invoke(
            value = value,
            offset = if (currentPage < 24) 0 else currentPage
        ).enqueue(object : Callback<GifsData> {
            override fun onResponse(call: Call<GifsData>, response: Response<GifsData>) {
                if (currentPage == 0)
                    _gifData.value = response.body()
                else
                    _gifData.value = _gifData.value?.copy(
                        data = buildList {
                            addAll(_gifData.value!!.data)
                            addAll(response.body()!!.data)
                        }
                    )
                currentPage += 24
                onLoadSuccess()
            }

            override fun onFailure(call: Call<GifsData>, t: Throwable) {
                onLoadFailure()
                Log.e("FAILURE GET", t.message.toString())
            }

        })
    }

    /**Sets page counter to 0.*/
    fun resetCurrentPage() {
        currentPage = 0
    }
}