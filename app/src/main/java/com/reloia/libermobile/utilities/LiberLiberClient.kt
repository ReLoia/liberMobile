package com.reloia.libermobile.utilities

import android.content.Context
import com.reloia.libermobile.model.BookItemData
import com.reloia.libermobile.model.ReadingOption
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
                    it.parent()?.parent()?.parent()?.select(".entry-title a")?.text() ?: "Error",
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

    // TODO: check full compatibility with any input
    private fun deParseString(string: String): String =
        string.replace(" ", "-").lowercase()
            .replace("à", "a")
            .replace("è", "e")
            .replace("é", "e")
            .replace("ì", "i")
            .replace("ò", "o")
            .replace("ù", "u")
            .replace("’", "")
            .replace(".", "")
            .replace(",", "")

    private fun urlFromTitleAndAuthor(title: String, author: String): String =
        "$baseURL/autori/autori-${deParseString(author).split("-").last().first().lowercase()}/${deParseString(author)}/${deParseString(title)}/"

    override val recentBookSelector: String = "h2.entry-title > a"

    override fun recentBookRequest(page: Int): Request = GET("$baseURL/category/progetti/manuzio/page/$page/")

    override fun parseRecentResults(document: Document): List<BookItemData> {
        val books = document.select(recentBookSelector)
        val bookList = mutableListOf<BookItemData>()

//        Log.w("LiberLiberClient", "parseRecentResults: $books")

        books.forEach {
            var fullTitleAndAuthor = it.text().split(" di ")
            val title = fullTitleAndAuthor[0].replace("“", "").replace("”", "")
            val author = fullTitleAndAuthor[1]

            bookList.add(
                BookItemData(
                    urlFromTitleAndAuthor(title, author),
                    title,
                    author,
                    it.parent()?.parent()?.parent()?.parent()?.select("img")?.attr("src"),
                ),
            )
        }
        return bookList
    }

    override fun parseMoreBookInfo(document: Document): BookItemData {
        val title = document.select(".ll_metadati_etichetta:contains(titolo) + .ll_metadati_dato").first()?.text() ?: ""
        val author = document.select(".ll_metadati_etichetta:contains(autore) + .ll_metadati_dato").text()
        val cover = document.select("#content .slides img").attr("src")
        val description = document.select(".ll_metadati_etichetta:contains(descrizione) + .ll_metadati_dato").text()

        val downloadOptions = document.select(".ll_opera_riga ~ a")

        return BookItemData(
            urlFromTitleAndAuthor(title, author),
            title,
            author,
            cover,
            description,
            downloadOptions.map {
                ReadingOption(
                    it.child(0).attr("alt"),
                    it.attr("href"),
                )
            },
        )
    }
}
