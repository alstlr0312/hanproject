package com.example.nftproject.network

import com.example.nftproject.model.*
import com.unity.mynativeapp.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
	@POST("/email/check")
	fun emailCheck(
		@Query("code") code: String
	): Call<MyResponse<String>>
	// 회원가입
	@POST("signup")
	fun signup(
		@Query("code") code: String,
		@Query("role") role: String,
		@Body signUpRequest: SignUpRequest
	) : Call<MyResponse<String>>

	//nft발행
	@Multipart
	@POST("/nft/save")
	fun makenft(
		@Part("issueNFTReq") issueNFTReq: RequestBody,
		@Part("countNFTReq") countNFTReq: RequestBody,
		@Part poster: MultipartBody.Part,
		@Part normal: MultipartBody.Part,
		@Part rare: MultipartBody.Part,
		@Part legend: MultipartBody.Part
	): Call<MyResponse<String>>

	//내가 발행한 nft 확인
	@GET("/nft")
	fun getmynft(
		@Query("sortType") postType: String?,
		@Query("username") username: String?,
		@Query("page") page: Int?,
		@Query("size") size: Int?
	) : Call<MyResponse<GetMyNftResponse>>


	// 내가 발행한 목록 상세 조회
	@GET("/nft/detail/{num}")
	fun getdetailmynft(
		@Path("num") num: Int
	) : Call<MyResponse<PnftResponse>>

	// 내가 구매한 nft 목록
	@GET("/nft/pick/list")
	fun getbuynft(
		@Query("username") username: String?,
		@Query("page") page: Int?,
		@Query("size") size: Int?
	) : Call<MyResponse<MyNftResponse>>

	//영화 목록 확인
	@GET("/movie")
	fun getmovie(
	): Call<MyResponse<GetMovieResponse>>


	// 영화 목록 상세 조회
	@GET("/movie/detail/{num}")
	fun getdetailmovie(
		@Path("num") num: Int
	) : Call<MyResponse<MovieDetailResponse>>

	//NFT구매
	@POST("nft/buy/{num}")
	fun buynft(
		@Path("num") num: Int
	) : Call<MyResponse<String>>

	//NFT교환
	@POST("nft/pick")
	fun exnft(@Body exchangeRequest: ExChangeRequest) : Call<MyResponse<exChangerResponse>>
}