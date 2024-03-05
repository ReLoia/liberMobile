package com.reloia.libermobile.ui.recent

import com.reloia.libermobile.data.BookItemData

interface RecentScreenRepository {
    suspend fun getRecentItems(): List<RecentItem>
}

// TODO: Replace with actual database or network interaction
class RecentScreenRepositoryImpl : RecentScreenRepository {
    override suspend fun getRecentItems(): List<RecentItem> {
//        delay(1000) // Simulating network delay
        return listOf(
            RecentItem(
                BookItemData(
                    1,
                    "Document X",
                    "autore",
                    "https://cdn.pixabay.com/photo/2016/09/07/10/37/kermit-1651325_1280.jpg"
                ),
                System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000
            ),
            RecentItem(
                BookItemData(
                    2,
                    "Image File",
                    "autore",
                ),
                System.currentTimeMillis() - 12 * 60 * 60 * 1000
            ),
            RecentItem(
                BookItemData(
                    3,
                    "Contact: John Doe",
                    "autore",
                    type = 1
                ),
                System.currentTimeMillis() - 30 * 60 * 1000
            )
        )
    }
}