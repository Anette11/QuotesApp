package com.example.quotesapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.quotesapp.navigation.QuotesNavHost
import com.example.quotesapp.ui.theme.QuotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContent {
            QuotesAppTheme {
                QuotesNavHost()
            }
        }
    }
}