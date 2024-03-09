package com.reloia.libermobile.ui.discover

import com.reloia.libermobile.model.BookItemData
import com.reloia.libermobile.model.SearchQueryData
import com.reloia.libermobile.utilities.LiberLiberClient

interface DiscoverScreenRepository {
    suspend fun getDiscoverData(): List<BookItemData>
}

class DiscoverScreenRepositoryImpl(
    private val liberLiberClient: LiberLiberClient,
    private val queryData: SearchQueryData,
) : DiscoverScreenRepository {
    override suspend fun getDiscoverData(): List<BookItemData> {
        var fetchedData: List<BookItemData> = emptyList()
        val discoverRequest =
            when (queryData.query) {
                "" -> liberLiberClient.recentBookRequest(queryData.page)
                else -> liberLiberClient.searchBookRequest(queryData.page, queryData.query)
            }

        liberLiberClient.client.newCall(discoverRequest).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Unexpected code $response")
            fetchedData =
                when (queryData.query) {
                    "" ->
                        liberLiberClient.parseRecentResults(
                            liberLiberClient.parseHTML(response.body!!.string()),
                        )
                    else ->
                        liberLiberClient.parseSearchResults(
                            liberLiberClient.parseHTML(response.body!!.string()),
                        )
                }
        }

        return fetchedData
    }
}
