package com.example.nftproject.model

import com.google.gson.annotations.SerializedName

data class NftMakeRequest(
    @SerializedName("movieTitle") val movieTitle: String,
    @SerializedName("movieGenre") val movieGenre: String,
    @SerializedName("filmRating") val filmRating: String,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("director") val director: String,
    @SerializedName("actors") val actors: String,
    @SerializedName("runningTime") val runningTime: Int,
    @SerializedName("normalNFTPrice") val normalNFTPrice: Int,
    @SerializedName("saleStartTime") val saleStartTime: String,
    @SerializedName("saleEndTime") val saleEndTime: String,
    @SerializedName("overView") val overView: String,
    @SerializedName("show") val show: Boolean,
    @SerializedName("exerciseDate") val exerciseDate: String
)