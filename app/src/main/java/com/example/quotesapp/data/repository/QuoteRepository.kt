package com.example.quotesapp.data.repository

import androidx.paging.PagingData
import com.example.quotesapp.data.local.dbo.ResultDbo
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    fun getQuotes(): Flow<PagingData<ResultDbo>>
}