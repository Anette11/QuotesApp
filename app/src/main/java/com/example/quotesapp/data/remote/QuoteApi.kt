package com.example.quotesapp.data.remote

import com.example.quotesapp.data.remote.dto.GetQuotesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {

    @GET("quotes")
    suspend fun getQuotes(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): GetQuotesResponse
}