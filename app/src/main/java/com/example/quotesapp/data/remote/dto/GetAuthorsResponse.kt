package com.example.quotesapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GetAuthorsResponse(
    val count: Int?,
    val lastItemIndex: Int?,
    val page: Int?,
    @SerializedName("results")
    val authors: List<Author> = emptyList(),
    val totalCount: Int?,
    val totalPages: Int?
)