package com.svdroid.avivgrouptest.ui.property.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.svdroid.avivgrouptest.R
import com.svdroid.avivgrouptest.data.ui.UIFilters
import com.svdroid.avivgrouptest.data.ui.UIPropertyModel
import com.svdroid.avivgrouptest.data.utils.DataState
import com.svdroid.avivgrouptest.ui.component.ErrorState
import com.svdroid.avivgrouptest.ui.component.LoadingState
import com.svdroid.avivgrouptest.ui.component.PropertiesList
import com.svdroid.avivgrouptest.ui.property.PropertiesActions

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun PropertiesListScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: PropertiesListViewModel = hiltViewModel(),
    actions: PropertiesActions,
    filters: UIFilters?
) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                viewModel.getProperties(filters)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val state by viewModel.state.collectAsState()
    val isRefreshing = state is DataState.Loading
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = { viewModel.refreshProperties(filters) })

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.properties)) }, actions = {
                if (state is DataState.Data) IconButton(
                    onClick = { actions.navigateToFilters(filters) },
                    content = {
                        BadgedBox(
                            badge = {
                                if (filters != null) Badge(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(top = 8.dp, end = 8.dp)
                                )
                            },
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_filter_list_24),
                                    contentDescription = ""
                                )
                            },
                        )
                    },
                )
            })
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {
                when {
                    state is DataState.Data && ((state as DataState.Data<*>).data as? List<*>)?.isEmpty() == true -> EmptyFilteredData(
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

                PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
            }
        }
    )
}

@Composable
fun EmptyFilteredData(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            Icons.Filled.Search,
            contentDescription = Icons.Filled.Search.name,
            modifier = Modifier.size(96.dp),
            tint = MaterialTheme.colorScheme.tertiaryContainer,
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.title_empty_properties),
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
        )
        Text(
            text = stringResource(id = R.string.message_empty_properties),
            style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center),
        )
    }
}