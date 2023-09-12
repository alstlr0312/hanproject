package com.example.nftproject.model

import com.google.gson.annotations.SerializedName

class MovieDetailResponse(
    @SerializedName("publisherName") val publisherName: String,
    @SerializedName("movieTitle") val movieTitle: String,
    @SerializedName("nftLevel") val nftLevel: String,
    @SerializedName("saleStartDate") val saleStartDate: String,
    @SerializedName("saleEndDate") val saleEndDate: String,
    @SerializedName("runningTime") val runningTime: Int,
    @SerializedName("normalNFTPrice") val normalNFTPrice: Int,
    @SerializedName("director") val director: String,
    @SerializedName("actors") val actors: List<String>,
    @SerializedName("filmRating") val filmRating: String,
    @SerializedName("movieGenre") val movieGenre: String,
    @SerializedName("description") val description: String,
   // @SerializedName("poster") val poster: String,
    @SerializedName("createdAt") val createdAt: String,
)