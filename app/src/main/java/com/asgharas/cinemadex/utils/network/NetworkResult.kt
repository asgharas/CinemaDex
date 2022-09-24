package com.asgharas.cinemadex.utils.network

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null) {

    class Error<T>(message: String): NetworkResult<T>(message=message)

    class Success<T>(data: T): NetworkResult<T>(data)

    class Loading<T>(): NetworkResult<T>()
}