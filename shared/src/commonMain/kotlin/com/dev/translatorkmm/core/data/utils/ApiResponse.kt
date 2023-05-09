package com.dev.translatorkmm.core.data.utils

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val error: Throwable) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}