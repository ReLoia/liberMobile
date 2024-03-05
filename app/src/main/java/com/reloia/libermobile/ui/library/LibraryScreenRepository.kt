package com.reloia.libermobile.ui.library

import com.reloia.libermobile.data.BookItemData

// Interface defining the contract for interacting with recent items data
interface RecentScreenRepository {
    suspend fun getRecentItems(): List<BookItemData>
}

// A possible implementation using a simulated network call
// You'd replace this with actual database or network interaction
class LibraryScreenRepositoryImpl : RecentScreenRepository {
    override suspend fun getRecentItems(): List<BookItemData> {
//        delay(1000) // Simulating network delay
        return listOf(
            BookItemData(
                1,
                "Document X",
                "autore",
                "https://cdn.pixabay.com/photo/2016/09/07/10/37/kermit-1651325_1280.jpg"
            ),
            BookItemData(
                2,
                "Image File",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
                type = 1
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            ),
            BookItemData(
                3,
                "Contact: John Doe",
                "autore",
            )
        )
    }
}