package com.reloia.libermobile.utilities

import android.content.Context
import android.util.Log
import com.reloia.libermobile.model.BookItemData
import okhttp3.Request
import org.jsoup.nodes.Document

class LiberLiberClient(context: Context) : HTTPClient(context) {
    override val baseURL: String = "https://liberliber.it"

    override val searchBookSelector: String = "a.fusion-read-more[href^='https://liberliber.it/autori/autori-']"

    override fun searchBookRequest(
        page: Int,
        query: String,
    ): Request = GET("$baseURL/page/$page/?s=$query")

    override fun parseSearchResults(document: Document): List<BookItemData> {
        val books = document.select(searchBookSelector)
        val bookList = mutableListOf<BookItemData>()
        books.forEach {
            bookList.add(
                BookItemData(
                    it.attr("href"),
                    it.attr("aria-label"),
                    parseAuthorName(it.attr("href").split("/")[3]),
                    // Image is not available in the search results
                ),
            )
        }
        return bookList
    }

    /**
     * Removes '-' from the author name and capitalizes the first letter of each word
     */
    private fun parseAuthorName(author: String): String =
        author.split("-").joinToString(" ") { it.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }

    // TODO: controllare che sia compatibile con qualsiasi input
    private fun deParseString(string: String): String = string.replace(" ", "-").lowercase().replace("'", "")

    override val listBookSelector: String = "h2.entry-title > a"

    override fun recentBookRequest(page: Int): Request = GET("$baseURL/category/progetti/manuzio/page/$page/")

    override fun parseRecentResults(document: Document): List<BookItemData> {
        val books = document.select(listBookSelector)
        val bookList = mutableListOf<BookItemData>()

        Log.w("LiberLiberClient", "parseRecentResults: $books")

        books.forEach {
            var fullTitleAndAuthor = it.text().split(" di ")
            val title = fullTitleAndAuthor[0].replace("“", "").replace("”", "")
            val author = fullTitleAndAuthor[1]

            bookList.add(
                BookItemData(
                    "$baseURL/autori/autori-${deParseString(author).split("-")[1].first().lowercase()}/${deParseString(author)}/${deParseString(title)}/",
                    title,
                    author,
                    it.parent()?.parent()?.parent()?.parent()?.select("img")?.attr("src"),
                ),
            )
        }
        return bookList
    }
}
