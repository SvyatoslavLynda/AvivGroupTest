package com.svdroid.avivgrouptest.ui.property.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.svdroid.avivgrouptest.R
import com.svdroid.avivgrouptest.data.mapper.toInfoRecords
import com.svdroid.avivgrouptest.data.ui.UIInfoRecord
import com.svdroid.avivgrouptest.data.ui.UIPropertyModel
import com.svdroid.avivgrouptest.data.utils.DataState
import com.svdroid.avivgrouptest.ui.component.ErrorState
import com.svdroid.avivgrouptest.ui.component.LoadingState
import com.svdroid.avivgrouptest.ui.property.PropertiesActions

@Composable
fun PropertyScreen(viewModel: PropertyViewModel = hiltViewModel(), propertyId: Long, actions: PropertiesActions) {
    val state by viewModel.getProperty(propertyId).collectAsState()

    Scaffold(
        content = { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                when {
                    state is DataState.Data && (state as DataState.Data<*>).data != null -> PropertyDetails(
                        property = ((state as DataState.Data<*>).data!! as UIPropertyModel),
                        actions = actions,
                        onFavoriteClick = { property -> viewModel.toggleFavorite(property) }
                    )
                    state is DataState.Error -> ErrorState(
                        modifier = Modifier.align(Alignment.Center),
                        (state as DataState.Error<*>).message,
                    )
                    state is DataState.Loading -> LoadingState(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    )
}

@Composable
fun PropertyDetails(
    property: UIPropertyModel,
    actions: PropertiesActions,
    onFavoriteClick: (property: UIPropertyModel) -> Unit,
) {
    val infoRecords = property.toInfoRecords()

    LazyColumn(
        content = {
            item {
                Box(
                    modifier = Modifier
                        .height(320.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
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
                            .padding(all = 16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.35f))
                            .align(Alignment.TopStart),
                        onClick = { actions.navigateUp.invoke() },
                        content = {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = Icons.Filled.Close.name,
                                tint = Color.White,
                            )
                        },
                    )

                    Text(
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.85f))
                            .padding(all = 16.dp)
                            .align(Alignment.BottomEnd),
                        text = property.professional,
                        style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White),
                    )

                    IconButton(
                        modifier = Modifier
                            .padding(all = 16.dp)
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
            item {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = property.propertyType,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                )
            }
            item {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Icon(painter = painterResource(id = R.drawable.city_24), contentDescription = property.city, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = property.city, style = TextStyle(fontSize = 16.sp, color = Color.Black))
                }
            }
            item {
                LazyRow(
                    modifier = Modifier.padding(16.dp),
                    content = {
                        items(infoRecords.size) { index ->
                            val infoRecord = infoRecords[index]

                            InfoItem(infoRecord)
                        }
                    }
                )
            }
        }
    )
}

@Composable
fun InfoItem(info: UIInfoRecord) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        Icon(painter = painterResource(id = info.iconId), contentDescription = info.info, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = info.info, style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.primary))
    }
}