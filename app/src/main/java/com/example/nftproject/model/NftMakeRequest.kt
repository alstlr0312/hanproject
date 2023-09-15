package com.example.nftproject.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NftMakeRequest(
    @SerializedName("movieTitle") val movieTitle: String?,
    @SerializedName("movieGenre") val movieGenre: String?,
    @SerializedName("filmRating") val filmRating: String?,
    @SerializedName("releaseDate") val releaseDate: String?,
    @SerializedName("director") val director: String?,
    @SerializedName("actors") val actors: Array<String>?,
    @SerializedName("runningTime") val runningTime: Int?,
    @SerializedName("normalNFTPrice") val normalNFTPrice: Int?,
    @SerializedName("saleStartDate") val saleStartDate: String?,
    @SerializedName("saleEndDate") val saleEndDate: String?,
    @SerializedName("overView") val overView: String?,
    @SerializedName("show") val show: Boolean?
): Serializable

data class NftcountRequest(
    @SerializedName("normalCount") val normalCount: Int?,
    @SerializedName("rareCount") val rareCount: Int?,
    @SerializedName("legendCount") val legendCount: Int?,
): Serializable