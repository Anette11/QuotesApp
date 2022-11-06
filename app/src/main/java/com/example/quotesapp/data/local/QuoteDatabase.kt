package com.example.quotesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quotesapp.data.local.dbo.ResultDbo
import com.example.quotesapp.data.local.util.Converters

@Database(
    entities = [ResultDbo::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class QuoteDatabase : RoomDatabase() {

    abstract val quoteDao: QuoteDao
}