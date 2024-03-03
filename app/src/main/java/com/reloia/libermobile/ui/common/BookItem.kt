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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

/**
 * A simple composable that displays a single book item
 *
 * @param id The unique ID for the book in liberLiber's database
 * @param title The title of the book
 * @param asset The asset url for the book's cover image
 */
@Composable
fun BookItem(
    id: Int,
    title: String,
    author: String,
    asset: String? = null,
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
//              TODO: Creare BookDetailActivity e fare in modo che venga aperta al click
                //            context.startActivity(
                //                BookDetailActivity.newIntent(context, id)
                //            )
            }
            .padding(12.dp, 8.dp)
    ) {
        AsyncImage(
            model = asset,
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
    }
}