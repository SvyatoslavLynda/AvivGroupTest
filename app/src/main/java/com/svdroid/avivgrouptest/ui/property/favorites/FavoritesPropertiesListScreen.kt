package com.svdroid.avivgrouptest.ui.property.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.svdroid.avivgrouptest.R
import com.svdroid.avivgrouptest.data.ui.UIPropertyModel
import com.svdroid.avivgrouptest.data.utils.DataState
import com.svdroid.avivgrouptest.ui.component.ErrorState
import com.svdroid.avivgrouptest.ui.component.LoadingState
import com.svdroid.avivgrouptest.ui.component.PropertiesList
import com.svdroid.avivgrouptest.ui.property.PropertiesActions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesPropertiesListScreen(
    viewModel: FavoritesPropertiesListViewModel = hiltViewModel(),
    actions: PropertiesActions,
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.favorite_properties)) }) },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                when {
                    state is DataState.Data && ((state as DataState.Data<*>).data as? List<*>)?.isEmpty() == true -> EmptyFavorites(
                        modifier = Modifier.align(Alignment.Center)
                    )
                    state is DataState.Data && (state as DataState.Data<*>).data != null -> PropertiesList(
                        items = ((state as DataState.Data<*>).data!! as List<*>).map { it as UIPropertyModel },
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
fun EmptyFavorites(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = Icons.Filled.Favorite.name,
            modifier = Modifier.size(96.dp),
            tint = MaterialTheme.colorScheme.tertiaryContainer,
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.title_empty_favorites),
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
        )
        Text(
            text = stringResource(id = R.string.message_empty_favorites),
            style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center),
        )
    }
}