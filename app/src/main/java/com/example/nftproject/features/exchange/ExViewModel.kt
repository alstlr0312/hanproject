package com.example.nftproject.features.exchange

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nftproject.model.ExChangeRequest
import com.example.nftproject.model.MyNftResponse
import com.example.nftproject.model.exChangerResponse
import com.example.nftproject.network.MyResponse
import com.unity.mynativeapp.model.LoginRequest
import com.unity.mynativeapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExViewModel: ViewModel() {

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _logout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = _logout

    private val _emyNftData = MutableLiveData<MyNftResponse?>()
    val emyNftData: LiveData<MyNftResponse?> = _emyNftData


    fun getmyPost(publisherName: String?= null, page: Int?=null, size: Int?=null) {

        _loading.postValue(true)
        getmyPostAPI(publisherName, page, size)
    }

    private fun getmyPostAPI(publisherName: String?, page: Int?, size: Int?) {
        RetrofitClient.getApiService().getbuynft(publisherName, page,size).enqueue(object :
            Callback<MyResponse<MyNftResponse>> {
            override fun onResponse(call: Call<MyResponse<MyNftResponse>>, response: Response<MyResponse<MyNftResponse>>) {
                _loading.postValue(false)

                val code = response.code()
                Log.d(ContentValues.TAG, code.toString())

                when(code) {
                    200 -> { // 내 게시물 목록 조회 요청 성공
                        val data = response.body()?.data
                        Log.d(ContentValues.TAG, data.toString())
                        data?.let {
                            _emyNftData.postValue(data)
                        }
                    }
                    400 -> {
                        _emyNftData.postValue(null)
                    }
                    401 -> _logout.postValue(true)
                    else -> {
                        Log.d(ContentValues.TAG, "$code")
                    }
                }
            }
            override fun onFailure(call: Call<MyResponse<MyNftResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "Error: ${t.message}")
                _loading.postValue(false)
            }
        })
    }

    fun exNftPost(
        nftSerialnum1: String, nftLevel1: String,
        nftSerialnum2: String, nftLevel2: String,
        nftSerialnum3: String, nftLevel3: String) {

        _loading.postValue(true)
        exNftPostAPI(nftSerialnum1, nftLevel1, nftSerialnum2, nftLevel2,nftSerialnum3,nftLevel3)
    }
    private val _newNftData = MutableLiveData<exChangerResponse?>()
    val newNftData: LiveData<exChangerResponse?> = _newNftData
    private fun exNftPostAPI(nftSerialnum1: String, nftLevel1: String,nftSerialnum2: String, nftLevel2: String,nftSerialnum3: String, nftLevel3: String) {
        RetrofitClient.getApiService().exnft(ExChangeRequest(nftSerialnum1, nftLevel1, nftSerialnum2, nftLevel2,nftSerialnum3,nftLevel3)).enqueue(object :
            Callback<MyResponse<exChangerResponse>> {
            override fun onResponse(call: Call<MyResponse<exChangerResponse>>, response: Response<MyResponse<exChangerResponse>>) {
                _loading.postValue(false)

                val code = response.code()
                Log.d(ContentValues.TAG, code.toString())

                when(code) {
                    201 -> {
                        val data = response.body()?.data
                        Log.d(ContentValues.TAG, data.toString())
                        data?.let {
                            _newNftData.postValue(data)
                        }
                    }
                    400 -> {
                        _emyNftData.postValue(null)
                    }
                    401 -> _logout.postValue(true)
                    else -> {
                        Log.d(ContentValues.TAG, "$code")
                    }
                }
            }
            override fun onFailure(call: Call<MyResponse<exChangerResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "Error: ${t.message}")
                _loading.postValue(false)
            }
        })
    }
}