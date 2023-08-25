package com.example.nftproject.network

import com.google.gson.annotations.SerializedName


data class Errordata<T>(
	@SerializedName("status")
	val status: Int,
	@SerializedName("error")
	val error: T? = null,
)

data class ErrordataList(
	@SerializedName("status")
	val status: Int,
	@SerializedName("error")
	val error: List<ErrordataItem>? = null,
)

data class ErrordataItem(val error: String)
