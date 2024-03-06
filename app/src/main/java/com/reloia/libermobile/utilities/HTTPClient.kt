package com.reloia.libermobile.utilities

// Requests libraries
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.reloia.libermobile.model.BookItemData
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.jsoup.nodes.Document

/**
 * Base class for HTTP clients, it is generic because in the future we may want to add more Websites
 */
abstract class HTTPClient(open var context: Context) {
    abstract val baseURL: String
    val userAgent = "Mozilla/5.0 (X11; Linux x86_64; rv:123.0) Gecko/20100101 Firefox/123.0"
    val headers: Headers by lazy { Headers.Builder().add("User-Agent", userAgent).build() }

    /**
     * Selector for the search page of the website
     *
     * e.g. "a.fusion-read-more[href^='https://liberliber.it/autori/autori-p/']"
     */
    abstract val searchBookSelector: String

    /**
     * Returns the Request URL for the search
     *
     * e.g. https://liberliber.it/?s=query
     * TODO: in the future implement filters
     */
    abstract fun searchBookRequest(
        page: Int,
        query: String,
//        filters:
    ): Request

    /**
     * Returns the list of books from the search results
     */
    abstract fun parseSearchResults(document: Document): List<BookItemData>

    abstract val listBookSelector: String

    abstract fun recentBookRequest(page: Int): Request

    abstract fun parseRecentResults(document: Document): List<BookItemData>

    open val client: OkHttpClient
        get() =
            OkHttpClient.Builder().addInterceptor { chain ->
                val request =
                    chain.request().newBuilder()
                        .headers(headers)
                        .build()
                chain.proceed(request)
            }.addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                },
            ).build()

    init {
        if (!isConnected()) {
            throw Exception("No network")
        }
    }

    /**
     * A function that checks if the device is connected to the internet
     */
    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun parseHTML(html: String): Document {
        return org.jsoup.Jsoup.parse(html)
    }

    fun GET(url: String): Request {
        return Request.Builder().url(url).headers(headers).build()
    }
}
