package com.example.quotesapp.data.local.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun listToJson(
        list: List<String>
    ): String = Gson().toJson(list)

    @TypeConverter
    fun jsonToList(
        json: String
    ): List<String> = Gson().fromJson(
        json,
        object : TypeToken<ArrayList<String>>() {}.type
    )
}