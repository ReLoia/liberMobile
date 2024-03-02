package com.reloia.libermobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.reloia.libermobile.ui.theme.LiberMobileTheme

class BrowseActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = intent.getStringExtra("type")

        setContent {
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
                        it
                        Text(text = "Hello, $type!, ${it.calculateBottomPadding()}")
                    }
                }
            }
        }
    }
}
