package com.vadesan.practica02dsm.data.model

import com.google.gson.annotations.SerializedName

data class SuffleResponse(
    val success: Boolean,
    @SerializedName("deck_id") val deck_id: String,
    val remaining: Int,
    val shuffled: Boolean
)
