package com.example.quotesapp.data.remote

import com.example.quotesapp.data.remote.dto.GetAuthorsResponse
import com.example.quotesapp.data.remote.dto.GetQuotesResponse
import com.example.quotesapp.data.remote.dto.Tag
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {

    @GET("quotes")
    suspend fun getQuotes(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): GetQuotesResponse

    @GET("tags")
    suspend fun getTags(): List<Tag>

    @GET("authors")
    suspend fun getAuthors(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): GetAuthorsResponse

    @GET("quotes")
    suspend fun getQuotesByTags(
        @Query("tags") tags: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): GetQuotesResponse

    @GET("quotes")
    suspend fun getQuotesByAuthor(
        @Query("author") author: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): GetQuotesResponse
}