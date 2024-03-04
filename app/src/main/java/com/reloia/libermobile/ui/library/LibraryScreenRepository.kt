package com.reloia.libermobile.ui.library

// Interface defining the contract for interacting with recent items data
interface RecentScreenRepository {
    suspend fun getRecentItems(): List<LibraryItem>
}

// A possible implementation using a simulated network call
// You'd replace this with actual database or network interaction
class LibraryScreenRepositoryImpl : RecentScreenRepository {
    override suspend fun getRecentItems(): List<LibraryItem> {
//        delay(1000) // Simulating network delay
        return listOf(
            LibraryItem(
                1,
                "Document X",
                System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000,
                "autore",
                "https://cdn.pixabay.com/photo/2016/09/07/10/37/kermit-1651325_1280.jpg"
            ),
            LibraryItem(
                2,
                "Image File",
                System.currentTimeMillis() - 12 * 60 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            ),
            LibraryItem(
                3,
                "Contact: John Doe",
                System.currentTimeMillis() - 30 * 60 * 1000,
                "autore",
            )
        )
    }
}