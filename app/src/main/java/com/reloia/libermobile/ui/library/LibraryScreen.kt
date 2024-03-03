package com.reloia.libermobile.ui.library

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import com.reloia.libermobile.ui.common.BookItem


@Composable
fun LibraryScreen(viewModel: LibraryScreenViewModel, filter: String? = null) {
    val recentItems by viewModel.recentItems.collectAsState(initial = null)

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            when (recentItems) {
                null -> CircularProgressIndicator()
                emptyList<LibraryItem>() -> Text("No recent items found")
                else -> {
                    LazyColumn {
                        Log.w("LibraryScreen", "Library items: $recentItems")
//                      Necessario perché recentItems diventa null dopo il controllo quando si cambia pagina
                        if (recentItems != null) {
                            items(recentItems!!.filter { it.title.contains(filter ?: "", ignoreCase = true) }) { item ->
                                BookItem(
                                    id = item.id,
                                    title = item.title,
                                    author = item.author,
                                    asset = item.cover_url
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}