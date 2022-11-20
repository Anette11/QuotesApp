package com.example.quotesapp.ui.components.random

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotesapp.data.local.PreferencesDataStoreProvider
import com.example.quotesapp.data.remote.dto.ResultDto
import com.example.quotesapp.data.remote.util.NetworkResource
import com.example.quotesapp.data.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val repository: QuoteRepository,
    private val preferencesDataStoreProvider: PreferencesDataStoreProvider
) : ViewModel() {

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    private val _randomQuote = MutableStateFlow<ResultDto?>(null)
    val randomQuote: StateFlow<ResultDto?> = _randomQuote.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var _isChecked = mutableStateOf(false)
    val isChecked: State<Boolean> = _isChecked

    fun onCheckedChange() {
        _isChecked.value = !isChecked.value
        viewModelScope.launch(Dispatchers.IO) {
            preferencesDataStoreProvider.saveIsChecked(isChecked.value)
        }
    }

    init {
        getRandomQuote()

        viewModelScope.launch(Dispatchers.IO) {
            val isChecked = preferencesDataStoreProvider.getIsChecked()
            _isChecked.value = isChecked
        }
    }

    fun getRandomQuote() = viewModelScope.launch(Dispatchers.IO) {
        _isLoading.emit(true)
        when (val response = repository.getRandomQuote()) {
            is NetworkResource.Success -> {
                val newRandomQuote = response.data
                _randomQuote.value = newRandomQuote
            }
            is NetworkResource.Failure -> _error.emit("${response.message}")
        }
        _isLoading.emit(false)
    }
}