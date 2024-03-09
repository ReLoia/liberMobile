package com.reloia.libermobile.model

/**
 * Simple data class to represent a reading option for a book
 * e.g:
 * PDF, ODT, ePUB, libro parlato
 */
data class ReadingOption(
    val name: String,
    val url: String,
)
