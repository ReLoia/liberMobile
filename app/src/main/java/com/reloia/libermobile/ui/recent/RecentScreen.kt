package com.reloia.libermobile.ui.recent

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.reloia.libermobile.model.BookItemData
import com.reloia.libermobile.ui.common.BookItem

@Composable
fun RecentScreen(
    viewModel: RecentScreenViewModel,
    isLoading: MutableState<Boolean> = mutableStateOf(false),
) {
    val recentItems by viewModel.recentItems.collectAsState(initial = null)

    Surface(
        modifier =
            Modifier
                .fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column {
            when (recentItems) {
                null -> 0 == 0 // CircularProgressIndicator()
                emptyList<RecentItem>() -> Text("No recent items found")
                else -> {
                    LazyColumn {
                        Log.w("RecentScreen", "Recent items: $recentItems")

//                      Necessario perché recentItems diventa null dopo il controllo quando si cambia pagina
                        if (recentItems != null) {
                            items(recentItems!!.sortedByDescending { it.timestamp }) { item ->
                                BookItem(
                                    BookItemData(
                                        item.bookData.url,
                                        item.bookData.title,
                                        item.bookData.author,
                                        item.bookData.cover_url
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}