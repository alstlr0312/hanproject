package com.example.nftproject.model

import com.google.gson.annotations.SerializedName

class NftMakeResponse(
    @SerializedName("issueNFTReq") val issueNFTReq: List<issueItem>,
    @SerializedName("countNFTReq") val countNFTReq: List<countItem>,
    @SerializedName("isFirst") val isFirst: Boolean
)

data class issueItem(
    @SerializedName("movieTitle") val movieTitle: String,
    @SerializedName("movieGenre") val movieGenre: String,
    @SerializedName("filmRating") val filmRating: String,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("director") val director: String,
    @SerializedName("actors") val actors: String,
    @SerializedName("runningTime") val runningTime: Int,
    @SerializedName("normalNFTPrice") val normalNFTPrice: Int,
    @SerializedName("saleStartDate") val saleStartDate: String,
    @SerializedName("saleEndDate") val saleEndDate: String,
    @SerializedName("overView") val overView: String,
    @SerializedName("show") val show: Boolean,
)

data class countItem(
    @SerializedName("normalCount") val normalCount: String,
    @SerializedName("rareCount") val rareCount: String,
    @SerializedName("legendCount") val legendCount: String,
)