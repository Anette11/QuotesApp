package com.example.quotesapp.util

import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes resInt: Int): String
}