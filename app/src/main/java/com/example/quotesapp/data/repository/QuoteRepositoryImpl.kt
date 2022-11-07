package com.example.quotesapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.quotesapp.data.local.QuoteDao
import com.example.quotesapp.data.local.QuoteDatabase
import com.example.quotesapp.data.local.QuoteRemoteKeysDao
import com.example.quotesapp.data.local.dbo.ResultDbo
import com.example.quotesapp.data.remote.QuoteApi
import com.example.quotesapp.data.remote.dto.Author
import com.example.quotesapp.data.remote.dto.ResultDto
import com.example.quotesapp.data.remote.dto.Tag
import com.example.quotesapp.data.remote.util.NetworkResource
import com.example.quotesapp.data.remote.util.RemoteConstants
import com.example.quotesapp.paging.QuotePagingSource
import com.example.quotesapp.paging.QuoteRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val quoteApi: QuoteApi,
    private val quoteDao: QuoteDao,
    private val quoteRemoteKeysDao: QuoteRemoteKeysDao,
    private val quoteDatabase: QuoteDatabase
) : QuoteRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getQuotes(): Flow<PagingData<ResultDbo>> =
        Pager(
            config = PagingConfig(pageSize = RemoteConstants.limit),
            remoteMediator = QuoteRemoteMediator(
                quoteApi = quoteApi,
                quoteDao = quoteDao,
                quoteRemoteKeysDao = quoteRemoteKeysDao,
                quoteDatabase = quoteDatabase
            ),
            pagingSourceFactory = {
                quoteDao.getQuotes()
            }
        ).flow

    override suspend fun getTags(): NetworkResource<List<Tag>> =
        try {
            val response = quoteApi.getTags()
            NetworkResource.Success(response)
        } catch (e: Exception) {
            NetworkResource.Failure("Error occurred")
        }

    override suspend fun getAuthors(
        page: Int,
        limit: Int
    ): NetworkResource<List<Author>> =
        try {
            val response = quoteApi.getAuthors(
                page = page,
                limit = limit
            )
            NetworkResource.Success(response.authors)
        } catch (e: Exception) {
            NetworkResource.Failure("Error occurred")
        }

    override fun getQuotesByTags(
        tags: String
    ): Flow<PagingData<ResultDto>> =
        Pager(
            config = PagingConfig(pageSize = RemoteConstants.limit),
            pagingSourceFactory = {
                QuotePagingSource(
                    tags = tags,
                    quoteApi = quoteApi
                )
            }
        ).flow

    override fun getQuotesByAuthor(
        author: String
    ): Flow<PagingData<ResultDto>> =
        Pager(
            config = PagingConfig(pageSize = RemoteConstants.limit),
            pagingSourceFactory = {
                QuotePagingSource(
                    author = author,
                    quoteApi = quoteApi
                )
            }
        ).flow
}