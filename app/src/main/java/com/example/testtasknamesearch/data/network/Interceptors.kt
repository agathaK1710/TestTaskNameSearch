package com.example.testtasknamesearch.data.network

import okhttp3.Interceptor
import okhttp3.Request
import java.net.InetAddress


private val CACHE_INTERCEPTOR = Interceptor { chain ->
    val originalResponse = chain.proceed(chain.request())
    val cacheControl = originalResponse.header("Cache-Control")
    if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
        cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")
    ) {
        originalResponse.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", "public, max-age=" + 5000)
            .build()
    } else {
        originalResponse
    }
}

private val FORCE_CACHE_INTERCEPTOR = Interceptor { chain ->
    var request: Request = chain.request()
    if (!isInternetAvailable()) {
        request = request.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", "public, only-if-cached")
            .build()
    }
    chain.proceed(request)
}

fun isInternetAvailable(): Boolean {
    return try {
        val ipAddr: InetAddress = InetAddress.getByName("google.com")
        !ipAddr.equals("")
    } catch (e: Exception) {
        false
    }
}