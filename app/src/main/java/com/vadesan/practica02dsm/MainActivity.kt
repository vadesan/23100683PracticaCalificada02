package com.vadesan.practica02dsm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vadesan.practica02dsm.presentation.navigation.AppNavGraph
import com.vadesan.practica02dsm.ui.theme.Practica02DSMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica02DSMTheme {
                AppNavGraph()
            }
        }
    }
}