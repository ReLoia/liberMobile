package com.reloia.libermobile.ui.recent

import kotlinx.coroutines.delay

// Interface defining the contract for interacting with recent items data
interface RecentScreenRepository {
    suspend fun getRecentItems(): List<RecentItem>
}

// A possible implementation using a simulated network call
// You'd replace this with actual database or network interaction
class RecentScreenRepositoryImpl : RecentScreenRepository {
    override suspend fun getRecentItems(): List<RecentItem> {
//        delay(1000) // Simulating network delay
        return listOf(
            RecentItem(1, "Document X", System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000),
            RecentItem(2, "Image File", System.currentTimeMillis() - 12 * 60 * 60 * 1000),
            RecentItem(3, "Contact: John Doe", System.currentTimeMillis() - 30 * 60 * 1000)
        )
    }
}