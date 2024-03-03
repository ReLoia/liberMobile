package com.reloia.libermobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import com.reloia.libermobile.ui.theme.LiberMobileTheme
import androidx.compose.runtime.*

class BrowseActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = intent.getStringExtra("type")
        val globalSearch = intent.getStringExtra("globalSearch")

        setContent {
            // String that will hold the real search query
            var search by remember {
                mutableStateOf(globalSearch ?: "")
            }
            // Debounced search query, to avoid making too many requests
            var toSearch by rememberSaveable {
                mutableStateOf(search)
            }
            LiberMobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold (
                        topBar = {
                            TopAppBar(
                                title = {
                                    if (type == "global") {
                                        TextField(
                                            value = toSearch,
                                            onValueChange = { toSearch = it },
                                            placeholder = {
                                                Text(
                                                    text = "Search...",
                                                    fontSize = 21.sp
                                                )
                                            },
                                            singleLine = true,
                                            keyboardOptions = KeyboardOptions.Default.copy(
                                                imeAction = ImeAction.Search
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onSearch = {
                                                    search = toSearch
                                                }
                                            ),
                                            trailingIcon = {
                                                if (toSearch.isNotEmpty()) {
                                                    IconButton(onClick = { toSearch = "" }) {
                                                        Icon(
                                                            Icons.Outlined.Clear,
                                                            contentDescription = "Clear search"
                                                        )
                                                    }
                                                }
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textStyle = TextStyle.Default.copy(fontSize = 21.sp),
                                            colors = TextFieldDefaults.colors(
                                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                                focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                                                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                                            ),
                                        )
                                    } else
                                        Text(text = when (type) {
                                            "author" -> stringResource(R.string.browse_author_title)
                                            "title" -> stringResource(R.string.browse_title_title)
                                            "argument" -> stringResource(R.string.browse_argument_title)

                                            else -> "Browse"
                                        })
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        finish()
                                    }) {
                                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                                    }
                                }
                            )
                        }
                    ) {
                        Surface (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
//                            when ()
                            Text(text = "search: $search")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BrowseScreen() {
    Column {
        Text(text = "Browse")
    }
}