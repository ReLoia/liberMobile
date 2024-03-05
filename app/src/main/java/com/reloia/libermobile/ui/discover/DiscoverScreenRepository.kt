package com.reloia.libermobile.ui.discover

import com.reloia.libermobile.data.BookItemData

interface DiscoverScreenRepository {
    suspend fun getDiscoverData(): List<BookItemData>
}

class DiscoverScreenRepositoryImpl : DiscoverScreenRepository {
    override suspend fun getDiscoverData(): List<BookItemData> {
        return emptyList()
    }
}
