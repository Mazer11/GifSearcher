package ru.internship.gifsearcher.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.internship.gifsearcher.data.dataclasses.GiffsData
import ru.internship.gifsearcher.data.remote.GifRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GifRepository
) : ViewModel() {

    private val _gifData: MutableLiveData<GiffsData> by lazy {
        MutableLiveData<GiffsData>()
    }
    val gifdata: LiveData<GiffsData> = _gifData

    private val _isLoadingState: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val isLoadingState: LiveData<Boolean> = _isLoadingState

    init {
        _isLoadingState.value = true
        viewModelScope.launch {
            getNewGiffs(onLoadSuccess = { _isLoadingState.value = false })
        }
    }

    private suspend fun getNewGiffs(
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

}