package com.example.nftproject.model

import com.google.gson.annotations.SerializedName
import com.unity.mynativeapp.model.LoginData

data class NewTokenResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("error") val error: String? = null,
    @SerializedName("data") val data: LoginData? = null,
)
