package com.reloia.libermobile.model

/**
 * Simple data class to represent a single book item

 * @param url The url of the book
 * @param title The title of the book
 * @param author The author of the book
 * @param cover_url The URL to the cover image of the book
 * @param description A description of the book
 * @param reading_options A list of reading options for the book

 */
data class BookItemData(
    val url: String,
    val title: String,
    val author: String,
//    val timestamp: Long,
    val cover_url: String? = null,
    val description: String? = null,
    val reading_options: List<ReadingOption>? = null,
)
