package com.example.nftproject.makerfeatures.mhome.detailfnt

import android.content.ContentValues
import android.util.Log
import androidx.constraintlayout.widget.StateSet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nftproject.model.PnftResponse
import com.example.nftproject.network.MyResponse
import com.unity.mynativeapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailNftViewModel: ViewModel() {
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _logout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = _logout

    private val _PnftDetailData = MutableLiveData<PnftResponse?>()
    val PnftDetailData: LiveData<PnftResponse?> = _PnftDetailData

    fun getNFtDetail(postId: Int) {

        _loading.postValue(true)

        getPnftDetailAPI(postId)
    }

    private fun getPnftDetailAPI(postId: Int) {
        RetrofitClient.getApiService().getdetailmynft(postId).enqueue(object :
            Callback<MyResponse<PnftResponse>> {
            override fun onResponse(
                call: Call<MyResponse<PnftResponse>>,
                response: Response<MyResponse<PnftResponse>>
            ) {
                _loading.postValue(false)

                val code = response.code()
                when (code) {
                    200 -> {
                        val data = response.body()?.data
                        Log.d(StateSet.TAG, data.toString())
                        data?.let {
                            _PnftDetailData.postValue(data)
                        }
                    }
                    401 -> _logout.postValue(true)
                    400 -> {
                        _PnftDetailData.postValue(null)
                    }
                    else -> {
                        Log.d(StateSet.TAG, "$code")
                    }
                }
            }
            override fun onFailure(call: Call<MyResponse<PnftResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "Error: ${t.message}")
                _loading.postValue(false)
            }
        })
    }
}