package com.sofascore.minisofa.data.remote

import android.util.Log
import java.io.IOException
import okhttp3.Interceptor
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.Response

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()
        Log.d("LoggingInterceptor", "Request URL: $url")
        try {
            val response = chain.proceed(request)
            val rawJson = response.peekBody(Long.MAX_VALUE).string()
            Log.d("LoggingInterceptor", "Response: $rawJson")
            return response
        } catch (e: IOException) {
            Log.e("LoggingInterceptor", "IOException occurred: ${e.message}")
            throw e
        }
    }
}