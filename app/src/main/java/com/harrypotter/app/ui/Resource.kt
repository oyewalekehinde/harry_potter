package com.harrypotter.app.ui

sealed class Resource<T> {
    data class Loading<T>(val isLoading: Boolean = true) : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: ErrorMessage) : Resource<T>()
}

open class ErrorMessage

data class NetworkTimeoutError(val message: String) : ErrorMessage()

data class NetworkError(val message: String) : ErrorMessage()

data class UnknownError(val message: String) : ErrorMessage()

data class ServerError(val message: String) : ErrorMessage()

data class NotFoundError(val message: String) : ErrorMessage()