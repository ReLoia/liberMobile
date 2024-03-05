package com.reloia.libermobile.data

/**
 * Simple data class to represent a single book item

 * @param id The unique identifier for the book
 * @param title The title of the book
 * @param author The author of the book
 * @param cover_url The URL to the cover image of the book
 * @param type The type of the book - 0 for regular books, 1 for audiobooks

 */
data class BookItemData(
    val id: Int,
    val title: String,
    val author: String,
//    val timestamp: Long,
    val cover_url: String? = null,
    val type: Int = 0
)
