package com.reloia.libermobile.ui.library

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
fun LibraryScreen(
    viewModel: LibraryScreenViewModel,
    // TODO: handle filters
    filter: String? = null,
    isLoading: MutableState<Boolean> = mutableStateOf(false),
) {
    val recentItems by viewModel.bookItem.collectAsState(initial = null)

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column {
            when (recentItems) {
                null -> 0 == 0 // CircularProgressIndicator()
                emptyList<BookItemData>() -> Text("No recent items found")
                else -> {
                    LazyColumn {
                        Log.w("LibraryScreen", "Library items: $recentItems")
//                      Necessario perché recentItems diventa null dopo il controllo quando si cambia pagina
                        if (recentItems != null) {
                            items(recentItems!!.filter { it.title.contains(filter ?: "", ignoreCase = true) }) { item ->
                                BookItem(item)
                            }
                        }
                    }
                }
            }
        }
    }
}