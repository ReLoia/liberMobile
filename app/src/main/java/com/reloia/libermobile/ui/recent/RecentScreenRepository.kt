package com.reloia.libermobile.ui.recent

import com.reloia.libermobile.model.BookItemData

interface RecentScreenRepository {
    suspend fun getRecentItems(): List<RecentItem>
}

// TODO:
class RecentScreenRepositoryImpl : RecentScreenRepository {
    override suspend fun getRecentItems(): List<RecentItem> {
//        delay(1000) // Simulating network delay
        return listOf(
            RecentItem(
                BookItemData(
                    "https://liberliber.it/autori/autori-0/autore-0",
                    "Document X",
                    "autore",
                    "https://cdn.pixabay.com/photo/2016/09/07/10/37/kermit-1651325_1280.jpg",
                ),
                System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000,
            ),
        )
    }
}