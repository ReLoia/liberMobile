package com.reloia.libermobile.ui.recent

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.reloia.libermobile.ui.common.BookItem

@Composable
fun RecentScreen(viewModel: RecentScreenViewModel) {
    val recentItems by viewModel.recentItems.collectAsState(initial = null)

    Surface(
        modifier =
            Modifier
                .fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column {
            when (recentItems) {
                // TODO: move to isLoading state
                null -> CircularProgressIndicator()
                emptyList<RecentItem>() -> Text("No recent items found")
                else -> {
                    LazyColumn {
                        Log.w("RecentScreen", "Recent items: $recentItems")

//                      Necessario perché recentItems diventa null dopo il controllo quando si cambia pagina
                        if (recentItems != null) {
                            items(recentItems!!.sortedByDescending { it.timestamp }) { item ->
                                BookItem(
                                    url = item.bookData.url,
                                    title = item.bookData.title,
                                    author = item.bookData.author,
                                    cover = item.bookData.cover_url,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}