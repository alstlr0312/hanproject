package com.example.nftproject.model

import android.net.Uri
import com.google.gson.annotations.SerializedName
import retrofit2.http.Multipart

data class GetMovieResponse(
    @SerializedName("movieListDtos") val movieListDtos: List<movieListItem>,
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("isFirst") val isFirst: Boolean
)

data class movieListItem(
    @SerializedName("id") val id: Int,
    @SerializedName("poster") val poster: String,
    @SerializedName("movieTitle")val movieTitle:String?
)