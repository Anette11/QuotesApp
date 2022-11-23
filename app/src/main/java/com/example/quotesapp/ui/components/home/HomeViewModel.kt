package com.example.quotesapp.ui.components.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotesapp.data.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    private var _showHomeDialog = mutableStateOf(false)
    val showHomeDialog: State<Boolean> = _showHomeDialog

    fun updateShowHomeDialog() {
        _showHomeDialog.value = !_showHomeDialog.value
    }

    val quotes = repository.getQuotes()

    fun deleteAllQuotes() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllQuotes()
    }
}