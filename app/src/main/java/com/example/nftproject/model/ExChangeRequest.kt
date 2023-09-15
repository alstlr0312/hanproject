package com.example.nftproject.model

import com.google.gson.annotations.SerializedName

class ExChangeRequest(
    @SerializedName("nftSerialnum1")
    val nftSerialnum1: String,
    @SerializedName("nftLevel1")
    val nftLevel1: String,
    @SerializedName("nftSerialnum2")
    val nftSerialnum2: String,
    @SerializedName("nftLevel2")
    val nftLevel2: String,
    @SerializedName("nftSerialnum3")
    val nftSerialnum3: String,
    @SerializedName("nftLevel3")
    val nftLevel3: String,
)