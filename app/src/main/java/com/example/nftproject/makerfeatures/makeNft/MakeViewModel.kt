package com.example.nftproject.makerfeatures.makeNft

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nftproject.network.Errordata
import com.example.nftproject.network.MyResponse
import com.example.nftproject.network.util.POST_WRITE_COMPLETE
import com.google.gson.GsonBuilder
import com.unity.mynativeapp.config.DialogActivity.Companion.DISMISS_LOADING
import com.unity.mynativeapp.config.DialogActivity.Companion.SHOW_TEXT_LOADING
import com.unity.mynativeapp.network.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MakeViewModel: ViewModel() {

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _loading = MutableLiveData<Int>()
    val loading: LiveData<Int> = _loading

    private val _logout = MutableLiveData<Boolean>(false)
    val logout: LiveData<Boolean> = _logout

    private val _postWriteSuccess = MutableLiveData<Boolean>()
    val postWriteSuccess: LiveData<Boolean> = _postWriteSuccess



    // 게시글 작성
    fun publishnft(body: RequestBody, body1: RequestBody, pimageFiles: MultipartBody.Part?, nimageFiles: MultipartBody.Part?, rimageFiles: MultipartBody.Part?, limageFiles: MultipartBody.Part?) {

        _loading.postValue(SHOW_TEXT_LOADING)

        publishnftApi(body, body1,pimageFiles,nimageFiles,rimageFiles,limageFiles)
    }

    private fun publishnftApi(body: RequestBody, body2: RequestBody, pimageFiles: MultipartBody.Part?, nimageFiles: MultipartBody.Part?, rimageFiles: MultipartBody.Part?, limageFiles: MultipartBody.Part?) {
        if (pimageFiles != null && nimageFiles != null && rimageFiles != null && limageFiles != null) {
                RetrofitClient.getApiService().makenft(body, body2, pimageFiles,nimageFiles,rimageFiles,limageFiles).enqueue(object :
                    Callback<MyResponse<String>> {
                    override fun onResponse(
                        call: Call<MyResponse<String>>,
                        response: Response<MyResponse<String>>
                    ) {
                        _loading.postValue(DISMISS_LOADING)

                        val code = response.code()
                        when(code) {
                            201 -> { // 게시글 작성 성공
                                val data = response.body()?.data
                                _toastMessage.postValue(POST_WRITE_COMPLETE)
                                _postWriteSuccess.postValue(true)
                            }
                            400 -> {
                                val body = response.errorBody()?.string()
                                val data = GsonBuilder().create().fromJson(body, Errordata::class.java)
                                Log.d(TAG, "$code  ${data.error.toString()}")
                            }
                            else -> {
                                val body = response.errorBody()?.string()
                                val data = GsonBuilder().create().fromJson(body, Errordata::class.java)
                                //Log.d(TAG, "$code  ${data.error.toString()}")
                              //  _toastMessage.postValue(data.error.toString())
                                if(code == 401) _logout.postValue(true)

                            }
                        }

                    }


                    override fun onFailure(call: Call<MyResponse<String>>, t: Throwable) {
                        Log.e(TAG, "Error: ${t.message}")
                        _loading.postValue(DISMISS_LOADING)

                    }
                })
            }


    }
}
