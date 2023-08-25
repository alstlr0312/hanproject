package com.example.nftproject.network

import com.unity.mynativeapp.model.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
	// 로그인
	@POST("signin")
	fun login(@Body loginRequest: LoginRequest): Call<MyResponse<LoginData>>
	// 이메일 인증 코드
	@POST("email")
	fun emailCode(@Body codeRequest: EmailCodeRequest): Call<MyResponse<EmailCodeResponse>>
	// 이메일 인증 코드 확인
	@POST("email/check")
	fun emailCheck(
		@Query("code") code: String
	): Call<MyResponse<String>>
	// 회원가입
	@POST("signup")
	fun signup(
		@Query("code") code: String,
		@Body signUpRequest: SignUpRequest
	) : Call<MyResponse<String>>


}