package com.example.nftproject.makerfeatures.mhome

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nftproject.model.GetMyNftResponse
import com.example.nftproject.network.MyResponse
import com.unity.mynativeapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MhomeViewModel: ViewModel() {

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _logout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = _logout

    private val _myNftData = MutableLiveData<GetMyNftResponse?>()
    val myNftData: LiveData<GetMyNftResponse?> = _myNftData


    fun myPost(sortType: String?=null,publisherName: String?= null, page: Int?=null, size: Int?=null) {

        _loading.postValue(true)
        getPostAPI(sortType, publisherName, page, size)
    }

    private fun getPostAPI(sortType: String?,publisherName: String?, page: Int?, size: Int?) {
        RetrofitClient.getApiService().getmynft(sortType, publisherName, page,size).enqueue(object :
            Callback<MyResponse<GetMyNftResponse>> {
            override fun onResponse(call: Call<MyResponse<GetMyNftResponse>>, response: Response<MyResponse<GetMyNftResponse>>) {
                _loading.postValue(false)

                val code = response.code()
                Log.d(ContentValues.TAG, code.toString())

                when(code) {
                    200 -> { // 내 게시물 목록 조회 요청 성공
                        val data = response.body()?.data
                        Log.d(ContentValues.TAG, data.toString())
                        data?.let {
                            _myNftData.postValue(data)
                        }
                    }
                    400 -> {
                        _myNftData.postValue(null)
                    }
                    401 -> _logout.postValue(true)
                    else -> {
                        Log.d(ContentValues.TAG, "$code")
                    }
                }
            }
            override fun onFailure(call: Call<MyResponse<GetMyNftResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "Error: ${t.message}")
                _loading.postValue(false)
            }
        })
    }
}