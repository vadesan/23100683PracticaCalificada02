package com.vadesan.practica02dsm.data.model

import com.google.gson.annotations.SerializedName

data class DrawResponse(
    val success: Boolean,
    val cards: List<Card>,
    @SerializedName("deck_id") val deckId: String,
    val remaining: Int
)

data class Card(
    val code: String,
    val image: String,
    val images: CardImages,
    val value: String,
    val suit: String
)

data class CardImages(
    val svg: String,
    val png: String
)