package com.example.quotesapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("_id")
    val id: String,
    val bio: String?,
    val dateAdded: String?,
    val dateModified: String?,
    val description: String?,
    val link: String?,
    val name: String?,
    val quoteCount: Int?,
    val slug: String?
)