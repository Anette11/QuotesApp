package com.example.quotesapp.ui.components.search.by_author

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.quotesapp.R
import com.example.quotesapp.data.remote.dto.ResultDto
import com.example.quotesapp.data.remote.util.NetworkResource
import com.example.quotesapp.data.remote.util.RemoteConstants
import com.example.quotesapp.data.repository.QuoteRepository
import com.example.quotesapp.ui.components.search.ChipItem
import com.example.quotesapp.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchByAuthorViewModel @Inject constructor(
    private val repository: QuoteRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _chips = mutableStateOf(emptyList<ChipItem>())
    val chips: State<List<ChipItem>> = _chips

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    private val _quotes = MutableStateFlow<PagingData<ResultDto>>(PagingData.empty())
    val quotes: StateFlow<PagingData<ResultDto>> = _quotes.asStateFlow()

    init {
        getAuthors()
    }

    fun onChipSelected(
        index: Int
    ) {
        val newChips = chips.value
        newChips[index].isSelected = !newChips[index].isSelected
        _chips.value = emptyList()
        _chips.value = newChips
        val authorsSelected = newChips
            .filter { it.isSelected }
            .joinToString(resourceProvider.getString(R.string.separator_for_tags)) { it.text }
        if (authorsSelected.isEmpty()) {
            _quotes.value = PagingData.empty()
        } else {
            getQuotesByAuthors(authorsSelected)
        }
    }

    private fun getAuthors() = viewModelScope.launch(Dispatchers.IO) {
        when (
            val result = repository.getAuthors(
                page = RemoteConstants.initialPage,
                limit = Int.MAX_VALUE
            )
        ) {
            is NetworkResource.Success -> {
                if (result.data == null || result.data.isEmpty()) {
                    _chips.value = emptyList()
                    return@launch
                }
                _chips.value = result.data
                    .filter { author -> author.name != null }
                    .map { author -> ChipItem(text = author.name.toString()) }
            }
            is NetworkResource.Failure ->
                withContext(Dispatchers.Main) {
                    _error.emit(result.message.toString())
                }
        }
    }

    private fun getQuotesByAuthors(
        authors: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.getQuotesByAuthor(authors)
            .cachedIn(viewModelScope)
            .collect {
                _quotes.value = it
            }
    }
}