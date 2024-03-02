package com.reloia.libermobile.ui.recent

// Simple data class to represent a single recent item
data class RecentItem(
    val id: Int, // You might want to use a more appropriate data type for the ID
    val title: String,
    val timestamp: Long // Assuming a timestamp to capture when the item was recent
)