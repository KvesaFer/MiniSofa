package com.sofascore.minisofa.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder().build() //.addQueryParameter("appid", API_KEY).build()
        return chain.proceed( request.newBuilder().url(url).build())
    }
}