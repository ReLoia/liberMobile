package com.reloia.libermobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reloia.libermobile.ui.theme.LiberMobileTheme

class BookInformationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val cover = intent.getStringExtra("cover")

        setContent {
            LiberMobileTheme {
                BookInformationActivity(url ?: "", title ?: "", author ?: "", cover ?: "")
            }
        }
    }
}

// Funzione temporanea per la preview di BookInformationActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun BookInformationActivity(
    url: String = "dioporco",
    title: String = "titolo M 0 0 0 0 0 0 0 0 0 0 0 ",
    author: String = "autore cognome",
    cover: String = "https://liberliber.it/wp-content/uploads/2024/02/muhlig_harvest_1200x0600.webp",
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = title, maxLines = 1)
                })
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
                            model = cover,
                            placeholder = painterResource(id = R.drawable.asset_error),
                            error = painterResource(id = R.drawable.asset_error),
                            contentDescription = "$title cover",
                            modifier = Modifier
                                .size(105.dp, 145.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Column(
                            modifier = Modifier
                                .align(alignment = androidx.compose.ui.Alignment.CenterVertically),
                        ) {
                            Text(
                                text = title,
                                fontSize = 28.sp,
                                modifier = Modifier.widthIn(max = 260.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = author,
                                fontSize = 16.sp,
                                modifier = Modifier.widthIn(max = 260.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Button(
                                onClick = { /*TODO*/ },
                                shape = MaterialTheme.shapes.small,
                            ) {
                                Text(text = "Continua")
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.size(8.dp))

                    // TODO: descrizione
                    Text(text = "Descrizione libro...")
                }
            }
        }
    }
}
