package com.vadesan.practica02dsm.presentation.deckofcardsapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadesan.practica02dsm.data.model.Card
import com.vadesan.practica02dsm.data.remote.deckofcards.DeckofcardsApiService
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GameViewModel : ViewModel() {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(Interceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            var tryCount = 0
            while (!response.isSuccessful && tryCount < 3) {
                tryCount++
                response.close()
                response = chain.proceed(request)
            }
            response
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://deckofcardsapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val api = retrofit.create(DeckofcardsApiService::class.java)

    var deckId by mutableStateOf("")

    var dealerSum by mutableStateOf(0)
    var playerSum by mutableStateOf(0)

    var isLoading: Boolean by mutableStateOf(false)

    fun shuffleCards(){
        viewModelScope.launch {
            try{
                isLoading = true
                playerSum = 0
                val result = api.shuffleCards()
                deckId = result.deck_id
                if (deckId.isNotEmpty()){
                    // random between 16 and 21
                    dealerSum = (16..21).random()
                }

            } catch (ex: Exception){
                deckId = "Error: ${ex.message}"
            } finally {
                isLoading = false
            }

        }
    }

    fun drawCards(count: Int){
        if (deckId.isBlank()) return

        viewModelScope.launch {
            try{
                isLoading = true
                val result = api.drawCards(deckId = deckId, count = count.toString())
                if (result.success){
                    val value = calculateCardsValue(result.cards)
                    playerSum += value
                }

            } catch (ex: Exception){
                deckId = "Error: ${ex.message}"
            } finally {
                isLoading = false
            }

        }
    }

    private fun calculateCardsValue(cards: List<Card>): Int {
        var total = 0
        var aces = 0
        for (card in cards) {
            when (card.value) {
                "ACE" -> {
                    aces += 1
                    total += 11 // Inicialmente, cuenta el As como 11
                }
                "KING", "QUEEN", "JACK" -> total += 10
                else -> total += card.value.toIntOrNull() ?: 0
            }
        }

        while (total > 21 && aces > 0) {
            total -= 10
            aces -= 1
        }
        return total
    }
}