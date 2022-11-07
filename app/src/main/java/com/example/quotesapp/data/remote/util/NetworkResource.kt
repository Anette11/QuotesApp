package com.example.quotesapp.data.remote.util

sealed class NetworkResource<T>(
    val data: T?,
    val message: String?
) {
    class Success<T>(
        data: T
    ) : NetworkResource<T>(
        data = data,
        message = null
    )

    class Failure<T>(
        message: String
    ) : NetworkResource<T>(
        data = null,
        message = message
    )
}