package com.reloia.libermobile.ui.bookinformation

import com.reloia.libermobile.model.BookItemData
import com.reloia.libermobile.utilities.LiberLiberClient

interface BookInformationRepository {
    suspend fun getBookInformation(): BookItemData
}

class BookInformationRepositoryImpl(
    private val liberLiberClient: LiberLiberClient,
    private val queryUrl: String,
) : BookInformationRepository {
    override suspend fun getBookInformation(): BookItemData {
        val document = liberLiberClient.parseHTML(liberLiberClient.client.newCall(liberLiberClient.GET(queryUrl)).execute().body!!.string())

        return liberLiberClient.parseMoreBookInfo(document)
    }
}
