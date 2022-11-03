package com.example.quotesapp.data.local.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "result_table")
data class ResultDbo(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val author: String?,
    val authorSlug: String?,
    val content: String?,
    val dateAdded: String?,
    val dateModified: String?,
    val length: Int?,
    val tags: List<String> = emptyList()
)