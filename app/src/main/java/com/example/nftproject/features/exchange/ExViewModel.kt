package com.example.nftproject.features.exchange

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nftproject.model.GetMyNftResponse
import com.example.nftproject.model.exChangerResponse
import com.example.nftproject.network.MyResponse
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

    private val _emyNftData = MutableLiveData<GetMyNftResponse?>()
    val emyNftData: LiveData<GetMyNftResponse?> = _emyNftData

    private val _exNftData = MutableLiveData<exChangerResponse?>()
    val exNftData: LiveData<exChangerResponse?> = _exNftData

    fun getmyPost(sortType: String?=null,publisherName: String?= null, page: Int?=null, size: Int?=null) {

        _loading.postValue(true)
        getmyPostAPI(sortType, publisherName, page, size)
    }

    private fun getmyPostAPI(sortType: String?,publisherName: String?, page: Int?, size: Int?) {
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
            override fun onFailure(call: Call<MyResponse<GetMyNftResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "Error: ${t.message}")
                _loading.postValue(false)
            }
        })
    }

    fun exNftPost(nftSerialnum1: Int, nftLevel1: String,nftSerialnum2: Int, nftLevel2: String,nftSerialnum3: Int, nftLevel3: String) {

        _loading.postValue(true)
        exNftPostAPI(nftSerialnum1, nftLevel1, nftSerialnum2, nftLevel2,nftSerialnum3,nftLevel3)
    }

    private fun exNftPostAPI(nftSerialnum1: Int, nftLevel1: String,nftSerialnum2: Int, nftLevel2: String,nftSerialnum3: Int, nftLevel3: String) {
        RetrofitClient.getApiService().exnft(nftSerialnum1, nftLevel1, nftSerialnum2, nftLevel2,nftSerialnum3,nftLevel3).enqueue(object :
            Callback<MyResponse<exChangerResponse>> {
            override fun onResponse(call: Call<MyResponse<exChangerResponse>>, response: Response<MyResponse<exChangerResponse>>) {
                _loading.postValue(false)

                val code = response.code()
                Log.d(ContentValues.TAG, code.toString())

                when(code) {
                    200 -> { // 내 게시물 목록 조회 요청 성공
                        val data = response.body()?.data
                        Log.d(ContentValues.TAG, data.toString())
                        data?.let {
                            _exNftData.postValue(data)
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