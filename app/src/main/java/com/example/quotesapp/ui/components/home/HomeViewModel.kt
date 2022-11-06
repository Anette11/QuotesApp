package com.example.quotesapp.ui.components.home

import androidx.lifecycle.ViewModel
import com.example.quotesapp.data.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: QuoteRepository
) : ViewModel() {

    val quotes = repository.getQuotes()
}