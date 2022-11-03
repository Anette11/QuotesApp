package com.example.quotesapp.data.remote.dto

data class GetQuotesResponse(
    val count: Int?,
    val lastItemIndex: Int?,
    val page: Int?,
    val results: List<ResultDto> = emptyList(),
    val totalCount: Int?,
    val totalPages: Int?
)