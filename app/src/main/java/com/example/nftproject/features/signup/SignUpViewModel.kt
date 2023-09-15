package com.example.nftproject.features.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nftproject.MyApplication
import com.example.nftproject.features.login.LoginViewModel
import com.example.nftproject.network.Errordata
import com.example.nftproject.network.MyResponse
import com.example.nftproject.network.util.*
import com.google.gson.GsonBuilder
import com.unity.mynativeapp.config.DialogActivity.Companion.DISMISS_LOADING
import com.unity.mynativeapp.config.DialogActivity.Companion.SHOW_LOADING
import com.unity.mynativeapp.config.DialogActivity.Companion.SHOW_TEXT_LOADING
import com.unity.mynativeapp.model.EmailCodeRequest
import com.unity.mynativeapp.model.EmailCodeResponse
import com.unity.mynativeapp.model.SignUpRequest
import com.unity.mynativeapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _loading = MutableLiveData<Int>()
    val loading: LiveData<Int> = _loading

    private val _checkSuccess = MutableLiveData<Boolean>(false)
    val checkSuccess: LiveData<Boolean> = _checkSuccess

    private val _signupSuccess = MutableLiveData<Boolean>(false)
    val signupSuccess: LiveData<Boolean> = _signupSuccess

    private var checkCode = ""
    fun check(email: String) {

        _loading.postValue(SHOW_LOADING)
        postCheckAPI(email)
    }

    private fun postCheckAPI(email: String) {
        RetrofitClient.getApiService().emailCode(EmailCodeRequest(email)).enqueue(object : Callback<MyResponse<EmailCodeResponse>> {
            override fun onResponse(call: Call<MyResponse<EmailCodeResponse>>, response: Response<MyResponse<EmailCodeResponse>>) {
                _loading.postValue(DISMISS_LOADING)
                val code = response.code()
                if (code == 200) {
                    val data = response.body()?.data ?: return
                    checkCode = data.code
                    _checkSuccess.postValue(true)
                    _toastMessage.postValue(EMAIL_CODE_SEND_SUCCESS)
                }else{
                    val body = response.errorBody()?.string()
                    val data = GsonBuilder().create().fromJson(body, Errordata::class.java)
                    _toastMessage.postValue(data.error.toString())
                }
            }


            override fun onFailure(call: Call<MyResponse<EmailCodeResponse>>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
                _loading.postValue(DISMISS_LOADING)
            }
        })
    }

    fun checkcode(code: String) {

        _loading.postValue(SHOW_LOADING)
        postCheckcode(code)
    }

    private fun postCheckcode(code: String) {
        RetrofitClient.getApiService().emailCheck(code).enqueue(object : Callback<MyResponse<String>> {
            override fun onResponse(call: Call<MyResponse<String>>, response: Response<MyResponse<String>>) {
                _loading.postValue(DISMISS_LOADING)
                val code = response.code()
                if (code == 200) {
                    _checkSuccess.postValue(true)
                    _toastMessage.postValue(EMAIL_CODE_SEND_SUCCESS)
                }else{
                    val body = response.errorBody()?.string()
                    val data = GsonBuilder().create().fromJson(body, Errordata::class.java)
                    _toastMessage.postValue(data.error.toString())
                }
            }


            override fun onFailure(call: Call<MyResponse<String>>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
                _loading.postValue(DISMISS_LOADING)
            }
        })
    }

    fun signup(id: String, password: String, passwordCheck: String, email: String, nickname: String,people:String, code: String) {

        _loading.postValue(SHOW_TEXT_LOADING)

        if (id.isEmpty()) {
            _toastMessage.postValue(ID_EMPTY_ERROR)
            return
        }

        if (password.isEmpty()) {
            _toastMessage.postValue(PW_EMPTY_ERROR)
            return
        }

        if (password != passwordCheck) {
            _toastMessage.postValue(PW_NOT_SAME_ERROR)
            return
        }

        if (email.isEmpty()) {
            _toastMessage.postValue(EMAIL_EMPTY_ERROR)
            return
        }

        if (nickname.isEmpty()) {
            _toastMessage.postValue(NICKNAME_EMPTY_ERROR)
            return
        }

       /* if(code != checkCode){
            _toastMessage.postValue(EMAIL_CODE_SAME_ERROR)
            return
        }*/

        RetrofitClient.getApiService().signup(code, people, SignUpRequest(id, password, nickname, email)).enqueue(object : Callback<MyResponse<String>> {
            override fun onResponse(call: Call<MyResponse<String>>, response: Response<MyResponse<String>>) {
                _loading.postValue(DISMISS_LOADING)

                val code = response.code()
                val data = response.body()?.data

                if (code == 201) { // 회원가입 성공
                    if (data == null) return

                    MyApplication.prefUtil.setString("username", data)
                    _toastMessage.postValue(SIGNUP_SUCCESS)
                    _signupSuccess.postValue(true)
                }
                else if(code == 400){ // 회원가입 실패
                    val body = response.errorBody()?.string()
                    val data = GsonBuilder().create().fromJson(body, Errordata::class.java)
                    val error = data.error.toString()
                    _toastMessage.postValue(error)
                    if(error.equals(EMAIL_DUPLICATE_ERROR)){ // 이메일 중복
                        _signupSuccess.postValue(false)
                    }
                }else{
                    Log.d(TAG, "$code  @${response.errorBody().toString()}")

                }
            }

            override fun onFailure(call: Call<MyResponse<String>>, t: Throwable) {
                Log.e(LoginViewModel.TAG, "Error: ${t.message}")
                _loading.postValue(DISMISS_LOADING)
            }
        })
    }

    companion object {
        const val TAG = "SignUpViewModel"
    }
}