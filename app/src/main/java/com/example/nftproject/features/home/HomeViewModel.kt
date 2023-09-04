package com.example.nftproject.features.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nftproject.model.GetMovieResponse
import com.example.nftproject.model.GetMyNftResponse
import com.example.nftproject.network.MyResponse
import com.unity.mynativeapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _logout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = _logout

    private val _myMovieData = MutableLiveData<GetMovieResponse?>()
    val myMovieData: LiveData<GetMovieResponse?> = _myMovieData

    fun getMovie() {
        _loading.postValue(true)
        getMovieAPI()
    }

    private fun getMovieAPI() {
        RetrofitClient.getApiService().getmovie().enqueue(object :
            Callback<MyResponse<GetMovieResponse>> {
            override fun onResponse(call: Call<MyResponse<GetMovieResponse>>, response: Response<MyResponse<GetMovieResponse>>) {
                _loading.postValue(false)

                val code = response.code()
                Log.d(ContentValues.TAG, code.toString())

                when(code) {
                    200 -> {
                        val data = response.body()?.data
                        Log.d(ContentValues.TAG, data.toString())
                        data?.let {
                            _myMovieData.postValue(data)
                        }
                    }
                    400 -> {
                        _myMovieData.postValue(null)
                    }
                    401 -> _logout.postValue(true)
                    else -> {
                        Log.d(ContentValues.TAG, "$code")
                    }
                }
            }
            override fun onFailure(call: Call<MyResponse<GetMovieResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "Error: ${t.message}")
                _loading.postValue(false)
            }
        })
    }
}