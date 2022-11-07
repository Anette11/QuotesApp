package com.example.quotesapp.data.repository

import androidx.paging.PagingData
import com.example.quotesapp.data.local.dbo.ResultDbo
import com.example.quotesapp.data.remote.dto.ResultDto
import com.example.quotesapp.data.remote.dto.Tag
import com.example.quotesapp.data.remote.util.NetworkResource
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    fun getQuotes(): Flow<PagingData<ResultDbo>>

    suspend fun getTags(): NetworkResource<List<Tag>>

    fun getQuotesByTags(tags: String): Flow<PagingData<ResultDto>>
}