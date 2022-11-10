package com.example.quotesapp.ui.components.random

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotesapp.data.remote.dto.ResultDto
import com.example.quotesapp.data.remote.util.NetworkResource
import com.example.quotesapp.data.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    private val _randomQuote = MutableStateFlow<ResultDto?>(null)
    val randomQuote: StateFlow<ResultDto?> = _randomQuote.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _selectedTime = mutableStateOf<String?>(null)
    val selectedTime: State<String?> = _selectedTime

    private var date: Date? = null

    fun onValueChange(date: Date) {
        val dateSelected = date
        val dateNow = Date()
        if (dateSelected.before(dateNow)) dateSelected.time =
            dateSelected.time.plus(24 * 60 * 60 * 1000L)
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val newValue = simpleDateFormat.format(date)
        _selectedTime.value = newValue
        this.date = dateSelected
    }

    init {
        getRandomQuote()
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

    fun findInitialDelay(): Long {
        val dateSet = date
        val dateNow = Date()
        val difference = dateSet?.time?.minus(dateNow.time)
        return difference ?: 0L
    }
}