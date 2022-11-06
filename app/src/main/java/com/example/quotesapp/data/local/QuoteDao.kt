package com.example.quotesapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quotesapp.data.local.dbo.ResultDbo

@Dao
interface QuoteDao {

    @Query("SELECT * FROM result_table")
    fun getQuotes(): PagingSource<Int, ResultDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveQuotes(list: List<ResultDbo>)

    @Query("DELETE FROM result_table")
    fun deleteAllQuotes()
}