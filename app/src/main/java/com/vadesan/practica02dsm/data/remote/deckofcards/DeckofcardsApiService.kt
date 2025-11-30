package com.vadesan.practica02dsm.data.remote.deckofcards

import com.vadesan.practica02dsm.data.model.DrawResponse
import com.vadesan.practica02dsm.data.model.SuffleResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeckofcardsApiService {

    @GET("api/deck/new/shuffle/?deck_count=1")
    suspend fun shuffleCards(

    ): SuffleResponse

    @GET("api/deck/{deck_id}/draw/")
    suspend fun drawCards(
        @Path("deck_id") deckId: String,
        @Query("count") count: String
    ): DrawResponse
}