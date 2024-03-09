package com.reloia.libermobile.ui.discover

import android.widget.Toast
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.reloia.libermobile.model.BookItemData
import com.reloia.libermobile.model.SearchQueryData
import com.reloia.libermobile.ui.common.BookItem
import com.reloia.libermobile.utilities.LiberLiberClient

@Composable
fun DiscoverScreen(
    search: String = "",
    isLoading: MutableState<Boolean> = mutableStateOf(false),
) {
    var currentPage by rememberSaveable { mutableStateOf(1) }
    val context = androidx.compose.ui.platform.LocalContext.current

    var queryData = SearchQueryData(search, currentPage)

    val viewModel: DiscoverScreenViewModel =
        try {
            DiscoverScreenViewModel(
                DiscoverScreenRepositoryImpl(
                    LiberLiberClient(context),
                    queryData,
                ),
            )
        } catch (e: Exception) {
            return Toast.makeText(context, "Nessuna connessione a internet", Toast.LENGTH_LONG).show()
        }

    val books by viewModel.bookItem.collectAsState(initial = null)
    // TODO: handle filters
    val filter = true

    Surface(
        modifier =
            Modifier
                .fillMaxSize(),
//                .padding(8.dp, 0.dp),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(1.dp),
        ) {
            when (books) {
//                null -> CircularProgressIndicator()
                null -> isLoading.value = true
                emptyList<BookItemData>() -> Text("No books found")
                else -> {
                    isLoading.value = false
                    LazyColumn {
                        if (books != null)
                            items(
                                books!!.filter {
                                    // TODO: implement filters
                                    true
                                },
                            ) { item ->
                                BookItem(item)
                            }

//                        TODO: implement pagination
//                        item {
//                            LaunchedEffect(true) {
//                                currentPage++
//                            }
//                        }
                    }
                }
            }
        }
    }
}
