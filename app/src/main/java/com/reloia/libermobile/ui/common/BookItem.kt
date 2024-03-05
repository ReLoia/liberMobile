package com.reloia.libermobile.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// TODO: Remove default values (used for preview)
// TODO: Switch from list of properties to a single data class
 /**
 * A simple composable that displays a single book item
 *
 * @param id The unique ID for the book in liberLiber's database
 * @param title The title of the book
* @param author The author of the book
 * @param cover The url for the book's cover image
* @param type The type of the book (0 for ebook, 1 for audiobook)
 */
@Preview(showBackground = true)
@Composable
fun BookItem(
    id: Int = 0,
    title: String = "Title",
    author: String = "Author",
    cover: String? = null,
    type: Int = 0
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
//              TODO: Creare BookDetailActivity e fare in modo che venga aperta al click
//                context.startActivity(
//                    BookDetailActivity.newIntent(context, id)
//                )
            }
            .padding(12.dp, 8.dp)
    ) {
        AsyncImage(
            model = cover,
            placeholder = painterResource(id = com.reloia.libermobile.R.drawable.asset_error),
            error = painterResource(id = com.reloia.libermobile.R.drawable.asset_error),
            contentDescription = "$title cover",
            modifier = Modifier
                .size(50.dp, 65.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column {
            Text(text = title)
            Text(text = author, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        }
        Spacer(modifier = Modifier.weight(1f))
//        TODO: vedere se rimuovere l'icona
        Icon(
            painter = painterResource(id = if (type == 0) com.reloia.libermobile.R.drawable.baseline_book_24 else com.reloia.libermobile.R.drawable.ic_audiobook),
            contentDescription = "Book type",
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}