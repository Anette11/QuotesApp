package com.example.quotesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quotesapp.data.local.dbo.QuoteRemoteKeys

@Dao
interface QuoteRemoteKeysDao {

    @Query("SELECT * FROM quote_remote_keys WHERE id = :id")
    fun getRemoteKey(id: String): QuoteRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRemoteKeys(list: List<QuoteRemoteKeys>)

    @Query("DELETE FROM quote_remote_keys")
    fun deleteAllRemoteKeys()
}