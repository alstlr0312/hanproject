package com.example.nftproject.model

import com.google.gson.annotations.SerializedName

class PnftResponse(
    @SerializedName("nftLevel") val nftLevel: String,
    @SerializedName("nftPrice") val nftPrice: Int,
    @SerializedName("nftCount") val nftCount: Int,
    @SerializedName("saleStartDate") val saleStartTime: String,
    @SerializedName("saleEndDate") val saleEndTime: String,
    @SerializedName("poster") val poster: String,
    @SerializedName("nftMedia") val nftMedia: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("movieTitle") val movieTitle: String
)