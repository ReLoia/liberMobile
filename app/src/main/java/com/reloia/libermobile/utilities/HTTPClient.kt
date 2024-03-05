package com.reloia.libermobile.utilities

// Requests libraries
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Headers
import okhttp3.OkHttpClient

//    private fun isConnected(): Boolean {
//        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val network = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
//        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//    }
abstract class HTTPClient (open var context: Context) {
    abstract val baseURL: String
    val userAgent = "Mozilla/5.0 (X11; Linux x86_64; rv:123.0) Gecko/20100101 Firefox/123.0"
    val headers: Headers by lazy { Headers.Builder().add("User-Agent", userAgent).build() }

    val searchBookSelector: String = "a.fusion-read-more[href^='https://liberliber.it/autori/autori-p/']"

    open val client: OkHttpClient
        get() = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .headers(headers)
                .build()
            chain.proceed(request)
        }.build()

    init {
        if (!isConnected()) {
            throw Exception("No internet connection")
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun searchBook(query: String): String {
        val url = "$baseURL/?s=$query"
        return client.newCall(okhttp3.Request.Builder().url(url).build()).execute().body!!.string()
    }
    abstract fun getBookDetails(url: String): String
    abstract fun getBookChapters(url: String): String
    abstract fun getChapterContent(url: String): String
    abstract fun getChapterAudio(url: String): String


}