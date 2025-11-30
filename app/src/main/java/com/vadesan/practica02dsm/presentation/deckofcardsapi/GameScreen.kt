package com.vadesan.practica02dsm.presentation.deckofcardsapi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun GameScreen(viewModel: GameViewModel= viewModel()){
    var numeroCartas by remember { mutableStateOf("2") }
    var enabled by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        viewModel.shuffleCards()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp))
    {
        if(viewModel.isLoading){
            CircularProgressIndicator()
        } else {
            Text(text = "Cod. juego:", style = MaterialTheme.typography.bodySmall)
            Text(text = viewModel.deckId, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Dealer con:", style = MaterialTheme.typography.bodyMedium)
            Text(text = viewModel.dealerSum.toString(), style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Jugador con:", style = MaterialTheme.typography.bodyMedium)
            Text(text = viewModel.playerSum.toString(), style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    value = numeroCartas,
                    onValueChange = { numeroElegido ->
                        if (numeroElegido.all { it.isDigit() } && numeroElegido.length <= 1) {
                            numeroCartas = numeroElegido
                        }
                    },
                    label = { Text("Cartas") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(90.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = {
                    val count = numeroCartas.toIntOrNull()
                    if (count != null && count in 2..5) {
                        viewModel.drawCards(count)
                    }
                    enabled = !viewModel.isLoading && numeroCartas.toIntOrNull() in 2..5
                }) {
                    Text(text = "Robar Carta")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.shuffleCards()
                        // Opcional: reiniciar el campo de texto
                        numeroCartas = "2"
                    },
                    enabled = !viewModel.isLoading
                ) {
                    Text(text = "Nuevo Juego")
                }

            }
        }
    }
}