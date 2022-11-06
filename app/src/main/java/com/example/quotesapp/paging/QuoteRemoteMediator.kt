package com.example.quotesapp.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.quotesapp.BuildConfig
import com.example.quotesapp.data.local.QuoteDao
import com.example.quotesapp.data.local.QuoteDatabase
import com.example.quotesapp.data.local.QuoteRemoteKeysDao
import com.example.quotesapp.data.local.dbo.QuoteRemoteKeys
import com.example.quotesapp.data.local.dbo.ResultDbo
import com.example.quotesapp.data.remote.QuoteApi
import com.example.quotesapp.data.remote.util.RemoteConstants
import com.example.quotesapp.data.util.toResultDbo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalPagingApi::class)
class QuoteRemoteMediator(
    private val quoteApi: QuoteApi,
    private val quoteDao: QuoteDao,
    private val quoteRemoteKeysDao: QuoteRemoteKeysDao,
    private val quoteDatabase: QuoteDatabase
) : RemoteMediator<Int, ResultDbo>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ResultDbo>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeysClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: RemoteConstants.initialPage
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstQuote(state)
                    remoteKeys?.prevKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastQuote(state)
                    remoteKeys?.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                }
            }
            val response = quoteApi.getQuotes(
                page = page,
                limit = RemoteConstants.limit
            ).results
            quoteDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    quoteDao.deleteAllQuotes()
                    quoteRemoteKeysDao.deleteAllRemoteKeys()
                }
                quoteDao.saveQuotes(response.map { resultDto -> resultDto.toResultDbo() })
                val remoteKeys = response.map { resultDto ->
                    QuoteRemoteKeys(
                        id = resultDto.id,
                        prevKey = if (page == 1) null else page.minus(1),
                        nextKey = if (response.isEmpty() || response.size < RemoteConstants.limit) null
                        else page.plus(1)
                    )
                }
                quoteRemoteKeysDao.saveRemoteKeys(remoteKeys)
            }
            return MediatorResult.Success(
                endOfPaginationReached = response.isEmpty() || response.size < RemoteConstants.limit
            )
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
            MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeysClosestToCurrentPosition(
        state: PagingState<Int, ResultDbo>
    ): QuoteRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                runBlocking(Dispatchers.IO) {
                    quoteRemoteKeysDao.getRemoteKey(id = id)
                }
            }
        }
    }

    private fun getRemoteKeyForFirstQuote(
        state: PagingState<Int, ResultDbo>
    ): QuoteRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { resultDbo ->
                runBlocking(Dispatchers.IO) {
                    quoteRemoteKeysDao.getRemoteKey(id = resultDbo.id)
                }
            }
    }

    private fun getRemoteKeyForLastQuote(
        state: PagingState<Int, ResultDbo>
    ): QuoteRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { resultDbo ->
                runBlocking(Dispatchers.IO) {
                    quoteRemoteKeysDao.getRemoteKey(id = resultDbo.id)
                }
            }
    }
}