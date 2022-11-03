package com.example.quotesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quotesapp.data.local.dbo.ResultDbo

@Database(
    entities = [ResultDbo::class],
    version = 1
)
abstract class QuoteDatabase : RoomDatabase() {

    abstract val quoteDao: QuoteDao
}