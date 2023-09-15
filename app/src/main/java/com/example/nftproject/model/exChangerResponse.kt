package com.example.nftproject.model

import com.google.gson.annotations.SerializedName

class exChangerResponse (
    @SerializedName("nftLevel") val nftLevel: String,
    @SerializedName("mediaUrl") val mediaUrl: String,
    @SerializedName("movieName") val movieName: String
)