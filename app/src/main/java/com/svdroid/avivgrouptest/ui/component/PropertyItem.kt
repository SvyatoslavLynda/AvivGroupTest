package com.svdroid.avivgrouptest.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.svdroid.avivgrouptest.R
import com.svdroid.avivgrouptest.data.ui.UIPropertyModel
import com.svdroid.avivgrouptest.ui.property.PropertiesActions

@Composable
fun PropertyItem(property: UIPropertyModel, actions: PropertiesActions, onFavoriteClick: (property: UIPropertyModel) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Box(
            modifier = Modifier
                .height(256.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable { actions.navigateToProperty.invoke(property) },
        ) {
            AsyncImage(
                model = property.url,
                contentDescription = property.toString(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_placeholder_24),
                error = painterResource(id = R.drawable.ic_placeholder_24),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
            )

            IconButton(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.35f))
                    .align(Alignment.TopEnd),
                onClick = { onFavoriteClick.invoke(property) },
                content = {
                    Icon(
                        if (property.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = Icons.Filled.Favorite.name,
                        tint = Color.White,
                    )
                },
            )
        }
    }
}