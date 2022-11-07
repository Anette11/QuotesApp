package com.example.quotesapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("_id")
    val id: String,
    val dateAdded: String?,
    val dateModified: String?,
    val name: String?,
    val quoteCount: Int?,
    val slug: String?
)