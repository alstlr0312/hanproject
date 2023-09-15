package com.example.nftproject.model

import com.google.gson.annotations.SerializedName

data class MyNftResponse(
    @SerializedName("nftPickDtos") val nftPickDtos: List<nftPickItem>,
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("isFirst") val isFirst: Boolean,
    @SerializedName("normalCount") val normalCount: Int,
    @SerializedName("rareCount") val rareCount: Int,
    @SerializedName("legendCount") val legendCount: Int,
)

data class nftPickItem (
    @SerializedName("poster") val poster: String,
    @SerializedName("movieTitle") val movieTitle: String,
    @SerializedName("nftLevel") val nftLevel: String,
    @SerializedName("nftSerialnum") val nftSerialnum: String,
)
