package com.example.quotesapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.quotesapp.data.remote.QuoteApi
import com.example.quotesapp.data.remote.dto.ResultDto
import com.example.quotesapp.data.remote.util.RemoteConstants

class QuotePagingSource(
    private val quoteApi: QuoteApi,
    private val tags: String? = null,
    private val author: String? = null
) : PagingSource<Int, ResultDto>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ResultDto> =
        try {
            val page = params.key ?: RemoteConstants.initialPage
            val quotes = if (tags != null) {
                quoteApi.getQuotesByTags(
                    tags = tags,
                    page = page,
                    limit = RemoteConstants.limit
                ).results
            } else if (author != null) {
                quoteApi.getQuotesByAuthor(
                    author = author,
                    page = page,
                    limit = RemoteConstants.limit
                ).results
            } else {
                emptyList()
            }
            LoadResult.Page(
                data = quotes,
                prevKey = if (page == RemoteConstants.initialPage) null else page.minus(1),
                nextKey = if (quotes.isEmpty() || quotes.size < RemoteConstants.limit) null
                else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(
        state: PagingState<Int, ResultDto>
    ): Int? = state.anchorPosition?.let { anchorPosition ->
        state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
}