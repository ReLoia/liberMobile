package com.reloia.libermobile.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reloia.libermobile.model.BookItemData
import com.reloia.libermobile.ui.bookinformation.BookInformationActivity

// TODO: Remove default values (used for preview)

/**
 * A simple composable that displays a single book item
 *
 * @param BookItemData item: the book item to display
 */
@Preview(showBackground = true)
@Composable
fun BookItem(
    item: BookItemData = BookItemData(
        "https://liberliber.it/autori/autori-0/autore-0",
        "Document X",
        "autore",
        "https://cdn.pixabay.com/photo/2016/09/07/10/37/kermit-1651325_1280.jpg",
    ),
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .clickable {
                context.startActivity(
                    android.content
                        .Intent(context, BookInformationActivity::class.java)
                        .putExtra("url", item.url)
                        .putExtra("title", item.title)
                        .putExtra("author", item.author)
                        .putExtra("cover", item.cover_url),
                )
            }
            .padding(12.dp, 8.dp),
    ) {
        AsyncImage(
            model = item.cover_url,
            placeholder = painterResource(id = com.reloia.libermobile.R.drawable.asset_error),
            error = painterResource(id = com.reloia.libermobile.R.drawable.asset_error),
            contentDescription = "${item.title} cover",
            modifier = Modifier
                .size(50.dp, 65.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column {
            Text(text = item.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = item.author, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}