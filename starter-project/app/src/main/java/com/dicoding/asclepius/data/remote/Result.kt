package com.dicoding.asclepius.data.remote

// Sealed class to represent the result of a data request
sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()      // Success state with data
    data class Error(val error: String): Result<Nothing>()    // Error state with message
    object Loading: Result<Nothing>()                         // Loading state without data
}
