package com.sofascore.minisofa.utils

import retrofit2.HttpException
import retrofit2.Response


suspend fun <T> safeResponse(func: suspend () -> T): Result<T> {
    return try {
        val result = func.invoke()
        if (result is Response<*> && result.isSuccessful.not()) {
            Result.failure(HttpException(result))
        } else {
            Result.success(result)
        }
    } catch (e: Throwable) {
        Result.failure(e)
    }
}