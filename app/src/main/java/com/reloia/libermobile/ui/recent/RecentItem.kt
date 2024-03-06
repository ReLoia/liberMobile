package com.reloia.libermobile.ui.recent

import com.reloia.libermobile.model.BookItemData

// Simple data class to represent a single recent item
data class RecentItem(
    val bookData: BookItemData,
    val timestamp: Long
)