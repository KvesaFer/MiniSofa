package com.sofascore.minisofa.data.remote

import com.google.gson.annotations.JsonAdapter


sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()

    class Error(val error: Throwable) : Result<Nothing>()
}