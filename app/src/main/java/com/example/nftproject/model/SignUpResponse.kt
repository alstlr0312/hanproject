package com.unity.mynativeapp.model

import com.google.gson.annotations.SerializedName


data class SignUpResponse(
    @SerializedName("error")
    val error: String? = null,
    @SerializedName("data")
    val data: String? = null
)
