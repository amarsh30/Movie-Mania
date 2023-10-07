package com.amr.moviemania.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amr.moviemania.R
import com.amr.moviemania.ui.theme.MovieManiaTheme

@Composable
fun MoviesFavoriteItem (
    moviesId: Long,
    image: Int,
    title: String,
    director: String,
    modifier: Modifier = Modifier
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(image),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(90.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1.0f)
            ) {
                Text(
                    text = title,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(
                    text = director,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }

@Composable
@Preview(showBackground = true)
fun MoviesFavoriteItemPreview() {
    MovieManiaTheme() {
        MoviesFavoriteItem(
            8, R.drawable.mencuri_raden_saleh,
                "Mencuri Raden Saleh",
                "Angga Dwimas Sasongko",
            )
        }
}