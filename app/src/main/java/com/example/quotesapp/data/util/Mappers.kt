package com.example.quotesapp.data.util

import com.example.quotesapp.data.local.dbo.ResultDbo
import com.example.quotesapp.data.remote.dto.ResultDto

fun ResultDto.toResultDbo() =
    with(this) {
        ResultDbo(
            id = id,
            author = author,
            authorSlug = authorSlug,
            content = content,
            dateAdded = dateAdded,
            dateModified = dateModified,
            length = length,
            tags = tags
        )
    }