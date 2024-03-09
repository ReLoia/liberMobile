package com.reloia.libermobile.ui.library

import com.reloia.libermobile.model.BookItemData

interface LibraryScreenRepository {
    suspend fun getLibraryItems(): List<BookItemData>
//    suspend fun addBookToLibrary(book: BookItemData)
}

class LibraryScreenRepositoryImpl : LibraryScreenRepository {
    override suspend fun getLibraryItems(): List<BookItemData> {
//        TODO: Implement loading from database
        return listOf(
            // Simulated data
            BookItemData(
                "https://liberliber.it/autori/autori-0/autore-0",
                "Document X",
                "autore",
                "https://cdn.pixabay.com/photo/2016/09/07/10/37/kermit-1651325_1280.jpg",
            ),
        )
    }
}