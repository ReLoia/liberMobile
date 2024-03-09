package com.reloia.libermobile.ui.bookinformation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import com.reloia.libermobile.R
import com.reloia.libermobile.model.BookItemData
import com.reloia.libermobile.model.ReadingOption
import com.reloia.libermobile.ui.theme.LiberMobileTheme
import com.reloia.libermobile.utilities.LiberLiberClient

class BookInformationActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.getStringExtra("url") ?: ""
        val title = intent.getStringExtra("title") ?: ""
        val author = intent.getStringExtra("author") ?: ""
        val cover = intent.getStringExtra("cover") ?: ""

        val viewModel = BookInformationViewModel(
            BookInformationRepositoryImpl(
                LiberLiberClient(this),
                url,
            ),
        )

        val context = this

        setContent {
            LiberMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
//                    TODO: caching and loading bookInformation - don't download book information if it's already in the viewmodel
                    val bookInformation by viewModel.bookItem.collectAsState(initial = null)
                    val bookData = (bookInformation ?: BookItemData(url, title, author, cover))

                    val view = LocalView.current
                    val inDarkMode = isSystemInDarkTheme()
                    val backgroundColor = MaterialTheme.colorScheme.background
                    val window = (context as Activity).window

                    var isTitleExpanded by rememberSaveable {
                        mutableStateOf(false)
                    }
                    var isDescriptionExpanded by rememberSaveable {
                        mutableStateOf(false)
                    }
        //        var isMoreExpanded by rememberSaveable {
        //            mutableStateOf(false)
        //        }

                    SideEffect {
                        window.statusBarColor = backgroundColor.toArgb()
                        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !inDarkMode
                    }
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
//                        Text(text = title, maxLines = 1)
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        context.finish()
                                    }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back",
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(
                                        onClick = {
                                            val gotoBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(bookData.url))
                                            context.startActivity(gotoBrowser)
                                        },
                                    ) {
                                        Icon(
                                            ImageVector.vectorResource(id = R.drawable.baseline_public_24),
                                            contentDescription = stringResource(R.string.open_in_browser),
                                        )
                                    }
//                        IconButton(
//                            onClick = {
//                                isMoreExpanded = !isMoreExpanded
//                            }
//                        ) {
//                            Icon(Icons.Default.MoreVert, contentDescription = "More")
//                        }
//                        DropdownMenu(expanded = isMoreExpanded, onDismissRequest = { isMoreExpanded = false }) {
//                            DropdownMenuItem(
//                                text = {
//                                    Text("Apri in browser")
//                                },
//                                onClick = {
//                                    val gotoBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
//                                    context.startActivity(gotoBrowser)
//                                },
//                            )
//                        }
                                },
                            )
                        },
                    ) {
                        Surface(
//                color = MaterialTheme.colorScheme.background,
                            modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(it),
//                color = androidx.compose.ui.graphics.Color.Blue,
                        ) {
                            Column(
                                modifier =
                                Modifier
                                    .fillMaxSize()
                                    .padding(8.dp, 8.dp),
//
                            ) {
                                Row(
                                    modifier =
                                    Modifier
                                        .fillMaxWidth(),
                                ) {
                                    AsyncImage(
                                        model = bookData.cover_url,
                                        placeholder = painterResource(id = R.drawable.asset_error),
                                        error = painterResource(id = R.drawable.asset_error),
                                        contentDescription = "${bookData.title} cover",
                                        modifier = Modifier
                                            .size(105.dp, 145.dp)
                                            .clip(RoundedCornerShape(6.dp)),
                                        contentScale = ContentScale.Crop,
                                    )
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Column(
                                        modifier = Modifier
                                            .align(alignment = androidx.compose.ui.Alignment.CenterVertically)
                                            .let {
                                                if (!isTitleExpanded) {
                                                    it.heightIn(max = 145.dp)
                                                } else {
                                                    it
                                                }
                                            }
                                            .padding(vertical = 8.dp),
                                    ) {
//                                        Spacer(modifier = Modifier.size(8.dp))
                                        Text(
                                            text = bookData.title,
                                            fontSize = 28.sp,
                                            // TODO: remove click animation
                                            modifier = Modifier.let {
                                                if (bookData.title.length > 22) {
                                                    it.clickable {
                                                        isTitleExpanded = !isTitleExpanded
                                                    }
                                                } else it
                                            },
                                            maxLines = if (isTitleExpanded) Int.MAX_VALUE else 1,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                        Text(
                                            text = bookData.author,
                                            fontSize = 16.sp,
                                            modifier = Modifier.widthIn(max = 260.dp),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                        Spacer(
                                            modifier =
                                                Modifier
                                                    .let {
                                                        if (isTitleExpanded) {
                                                            it.size(12.dp)
                                                        } else {
                                                            it.weight(1f)
                                                        }
                                                    },
                                        )
                                        Row {
//                              TODO: check if book is in library
                                            var isInLibrary by rememberSaveable {
                                                mutableStateOf(false)
                                            }

                                            Row(
                                                modifier = Modifier
                                                    .align(alignment = androidx.compose.ui.Alignment.CenterVertically)
                                                    .background(
                                                        color = MaterialTheme.colorScheme.primary,
                                                        shape = RoundedCornerShape(18.dp),
                                                    ),
                                            ) {
                                                IconButton(
                                                    onClick = {
                                                        // TODO: add to library
                                                        isInLibrary = !isInLibrary

                                                        Toast.makeText(
                                                            context,
                                                            if (isInLibrary) getString(R.string.library_added) else getString(
                                                                R.string.library_removed
                                                            ),
                                                            Toast.LENGTH_SHORT,
                                                        ).show()
                                                    },
                                                    modifier = Modifier.size(38.dp)) {
                                                    Crossfade(targetState = isInLibrary, label = "") {
                                                        Icon(
                                                            ImageVector.vectorResource(id = if (it) R.drawable.baseline_local_library_24 else R.drawable.outline_local_library_24),
                                                            contentDescription = stringResource(R.string.library_description),
                                                            tint = MaterialTheme.colorScheme.onPrimary,
                                                            modifier = Modifier.size(24.dp),
                                                        )
                                                    }
                                                }
                                                Spacer(modifier = Modifier.size(8.dp))
                                                IconButton(
                                                    onClick = {
//                                        TODO: play button
                                                    },
                                                    modifier = Modifier.size(38.dp),
                                                ) {
                                                    Icon(
                                                        Icons.Default.PlayArrow,
                                                        contentDescription = "Play",
                                                        tint = MaterialTheme.colorScheme.onPrimary,
                                                        modifier = Modifier.size(28.dp),
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.size(8.dp))

                                Text(
                                    text = (bookData.description ?: stringResource(R.string.book_description_not_available)) + "\n\n" +
                                        stringResource(R.string.book_description_more_infos),
                                    maxLines = if (isDescriptionExpanded) Int.MAX_VALUE else 3,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.clickable {
                                        isDescriptionExpanded = !isDescriptionExpanded
                                    },
                                )
                                if (!isDescriptionExpanded && (bookData.description?.length ?: 0) > 150) {
                                    Text(
                                        text = stringResource(R.string.show_more),
                                        modifier = Modifier.clickable {
                                            isDescriptionExpanded = true
                                        },
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                }

                                Spacer(modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier
                                    .height((.5).dp)
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)),)
                                Spacer(modifier = Modifier.size(12.dp))

                                Text(
                                    text = stringResource(R.string.reading_options),
                                    fontSize = 24.sp,
                                )
                                Spacer(modifier = Modifier.size(16.dp))
                                Column {
                                    bookData.reading_options?.forEachIndexed { index, it ->
                                        BookReadingOption(it)
                                        if (index != bookData.reading_options.size - 1) {
                                            Spacer(modifier = Modifier
                                                .height(1.dp)
                                                .fillMaxWidth()
                                                .background(
                                                    MaterialTheme.colorScheme.secondary.copy(
                                                        alpha = 0.5f
                                                    )
                                                ),
                                            )
                                        }
                                    }
                                    if (bookData.reading_options.isNullOrEmpty()) {
                                        Text(
                                            text = stringResource(R.string.no_available_reading_option),
                                            fontSize = 18.sp,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookReadingOption(readingOption: ReadingOption) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
//          TODO: take to an activity to read the book

            },
    ) {
        Row {
            Text(
                text = readingOption.name,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(4.dp, 16.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /*TODO download button*/ }) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.baseline_download_24),
                    contentDescription = "Download",
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}
