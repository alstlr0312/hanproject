package com.example.nftproject.model

import com.google.gson.annotations.SerializedName

class GetMyNftResponse (
    @SerializedName("nftListDtos") val nftListDtos: List<nftListItem>,
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("isFirst") val isFirst: Boolean
)

data class nftListItem(
    @SerializedName("movieTitle") val movieTitle: String,
    @SerializedName("nftPrice") val nftPrice: Int,
    @SerializedName("nftCount") val nftCount: Int,
    @SerializedName("runningTime") val runningTime: Int,
    @SerializedName("saleStartDate") val saleStartDate: String?,
    @SerializedName("saleEndDate") val saleEndDate: String?,
)

