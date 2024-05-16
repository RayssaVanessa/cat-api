package com.example.catapi.data.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
internal data class ListCatResponse(
 @SerializedName("id") val id: String,
 @SerializedName("url") val url: String
)
