package com.developerCompany.calculadoraDePropinas.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.developerCompany.calculadoraDePropinas.ui.theme.TipAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalcActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipAppTheme (dynamicColor = false){
                CalcScreen()
            }
        }
    }
}
