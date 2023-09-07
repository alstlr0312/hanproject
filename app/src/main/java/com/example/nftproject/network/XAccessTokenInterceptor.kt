package com.example.nftproject.network


import android.util.Log
import androidx.annotation.RestrictTo.Scope
import com.example.nftproject.BuildConfig
import com.example.nftproject.MyApplication
import com.example.nftproject.model.NewTokenResponse
import com.example.nftproject.network.util.*
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class XAccessTokenInterceptor : Interceptor {

	@Throws(IOException::class)
	override fun intercept(chain: Interceptor.Chain): Response {
		var request = chain.request()
		val builder: Request.Builder = request.newBuilder()

		val jwtToken: String? = MyApplication.prefUtil.getString(X_ACCESS_TOKEN, null)
		if (jwtToken != null) {
			builder.addHeader(AUTHORIZATION, "$GRANT_TYPE $jwtToken")
		}

		request = builder.build()
		val response = chain.proceed(request)
		Log.d(TAG , response.code.toString()+"sfdksljf")


		if (jwtToken!=null && response.code == 401) { // 사용하던 토큰이 만료되었다면
			Log.d(TAG, "토큰 만료됨")

			val currentToken = MyApplication.prefUtil.getString(X_ACCESS_TOKEN, null)

			if(currentToken != null && currentToken == jwtToken) { // 토큰이 재발급 안되었다면
				val interceptor = XAccessTokenInterceptor()
				val code = runBlocking { interceptor.refreshToken() }
				if(code != 200) { // 재발급이 안되었다면
					return response
				}
			}
			val newToken = MyApplication.prefUtil.getString(X_ACCESS_TOKEN, null)
			if(newToken != null) { // 재발급한 토큰으로 재요청하기
				request = builder
					.header(AUTHORIZATION, "$GRANT_TYPE $newToken")
					.removeHeader(REFRESH_TOKEN)
					.build()
				return chain.proceed(request)
			}

		}else{
			Log.d(TAG, "토큰 없거나 유효함")
		}
		return response
	}


	private suspend fun refreshToken(): Int { // 토큰 재발급
		return withContext(Dispatchers.IO) {
			val refreshRequest = Request.Builder()
				.url("http://3.36.109.82:8080/")
				.get()
				.addHeader(AUTHORIZATION, "$GRANT_TYPE ${MyApplication.prefUtil.getString(X_ACCESS_TOKEN, null).toString()}")
				.addHeader(REFRESH_TOKEN, "$GRANT_TYPE ${MyApplication.prefUtil.getString(X_REFRESH_TOKEN, null).toString()}")
				.build()

			val client = OkHttpClient.Builder().build()

			try {
				val refreshResponse = client.newCall(refreshRequest).execute()

				if (refreshResponse.isSuccessful) {
					val body = refreshResponse.body?.string()
					val data = GsonBuilder().create().fromJson(body, MyResponse::class.java)
					if (data.status != 200) { // 토큰 재발급 실패
						println("${data.status}")
						Log.d("applicationClass", "토큰 재발급 실패 ${data.status}")
					} else { // 토큰 재발급 성공
						val newData =
							GsonBuilder().create().fromJson(body, NewTokenResponse::class.java)
						Log.d("applicationClass", "토큰 재발급 성공")

						newData.data?.let { newTokens ->
							MyApplication.prefUtil.setString(X_ACCESS_TOKEN, newTokens.accessToken)
							MyApplication.prefUtil.setString(X_REFRESH_TOKEN, newTokens.refreshToken)
						} ?: run {
							Log.d("applicationClass", "실패")
						}
					}
					data.status
				} else {
					refreshResponse.code
				}
			} catch (e: IOException) {
				400
			}
		}
	}

	companion object {
		const val TAG = "accessTokenInterceptor"
	}
}