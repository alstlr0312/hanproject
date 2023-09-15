package com.example.nftproject.features.home

import android.content.ContentValues
import android.util.Log
import androidx.constraintlayout.widget.StateSet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nftproject.model.GetMovieResponse
import com.example.nftproject.model.GetMyNftResponse
import com.example.nftproject.model.MovieDetailResponse
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

    private val _postDetailData = MutableLiveData<MovieDetailResponse?>()
    val postDetailData: LiveData<MovieDetailResponse?> = _postDetailData

    private val _postBuyNftSuccess = MutableLiveData<Boolean>()
    val postBuyNftSuccess: LiveData<Boolean> = _postBuyNftSuccess

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

    //상세조회
    fun getMovieDetail(postId: Int) {

        _loading.postValue(true)

        getPostDetailAPI(postId)
    }

    private fun getPostDetailAPI(postId: Int) {
        RetrofitClient.getApiService().getdetailmovie(postId).enqueue(object :
            Callback<MyResponse<MovieDetailResponse>> {
            override fun onResponse(
                call: Call<MyResponse<MovieDetailResponse>>,
                response: Response<MyResponse<MovieDetailResponse>>
            ) {
                _loading.postValue(false)

                val code = response.code()
                when (code) {
                    200 -> { // 게시글 상세 조회 성공
                        val data = response.body()?.data
                        Log.d(StateSet.TAG, data.toString())
                        data?.let {
                            _postDetailData.postValue(data)
                        }
                    }
                    401 -> _logout.postValue(true)
                    400 -> {// 존재하지 않는 게시글
                        _postDetailData.postValue(null)
                    }
                    else -> {
                        Log.d(StateSet.TAG, "$code")
                    }
                }
            }


            override fun onFailure(call: Call<MyResponse<MovieDetailResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "Error: ${t.message}")
                _loading.postValue(false)
            }
        })
    }

    //nft 구매
    fun getBuyNft(num: Int) {

        _loading.postValue(true)

        getBuyNftAPI(num)
    }

    private fun getBuyNftAPI(num: Int) {
        RetrofitClient.getApiService().buynft(num).enqueue(object :
            Callback<MyResponse<String>> {
            override fun onResponse(
                call: Call<MyResponse<String>>,
                response: Response<MyResponse<String>>
            ) {
                _loading.postValue(false)

                val code = response.code()
                when (code) {
                    200 -> { // 게시글 상세 조회 성공
                        val data = response.body()?.data
                        Log.d(StateSet.TAG, data.toString())
                        data?.let {
                            _postBuyNftSuccess.postValue(true)
                        }
                    }
                    401 -> _logout.postValue(true)
                    400 -> {
                        _postBuyNftSuccess.postValue(false)
                    }
                    else -> {
                        Log.d(StateSet.TAG, "$code")
                    }
                }
            }


            override fun onFailure(call: Call<MyResponse<String>>, t: Throwable) {
                Log.e(ContentValues.TAG, "Error: ${t.message}")
                _loading.postValue(false)
            }
        })
    }
}