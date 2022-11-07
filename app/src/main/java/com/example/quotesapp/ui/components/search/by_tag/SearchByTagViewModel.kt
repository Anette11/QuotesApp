package com.example.quotesapp.ui.components.search.by_tag

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.quotesapp.data.remote.dto.ResultDto
import com.example.quotesapp.data.remote.util.NetworkResource
import com.example.quotesapp.data.repository.QuoteRepository
import com.example.quotesapp.ui.components.search.ChipItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchByTagViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _chips = mutableStateOf(emptyList<ChipItem>())
    val chips: State<List<ChipItem>> = _chips

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    private val _quotes = MutableStateFlow<PagingData<ResultDto>>(PagingData.empty())
    val quotes: StateFlow<PagingData<ResultDto>> = _quotes.asStateFlow()

    init {
        getTags()
    }

    fun onChipSelected(
        index: Int
    ) {
        val newChips = chips.value
        newChips[index].isSelected = !newChips[index].isSelected
        _chips.value = emptyList()
        _chips.value = newChips
        val tagsSelected = newChips.filter {
            it.isSelected
        }.joinToString("|") {
            it.text
        }
        if (tagsSelected.isEmpty()) {
            _quotes.value = PagingData.empty()
        } else {
            getQuotesByTags(tagsSelected)
        }
    }

    private fun getTags() = viewModelScope.launch(Dispatchers.IO) {
        when (val result = repository.getTags()) {
            is NetworkResource.Success -> {
                if (result.data == null || result.data.isEmpty()) {
                    _chips.value = emptyList()
                    return@launch
                }
                _chips.value = result.data
                    .filter { tag -> tag.name != null }
                    .map { tag -> ChipItem(text = tag.name.toString()) }
            }
            is NetworkResource.Failure ->
                withContext(Dispatchers.Main) {
                    _error.emit(result.message.toString())
                }
        }
    }

    private fun getQuotesByTags(
        tags: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.getQuotesByTags(tags)
            .cachedIn(viewModelScope)
            .collect {
                _quotes.value = it
            }
    }
}