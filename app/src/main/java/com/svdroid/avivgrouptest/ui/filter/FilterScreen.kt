package com.svdroid.avivgrouptest.ui.filter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.svdroid.avivgrouptest.R
import com.svdroid.avivgrouptest.data.ui.UIFilters
import com.svdroid.avivgrouptest.ui.main.MainActions
import com.svdroid.avivgrouptest.utils.extension.convertAreaToString
import com.svdroid.avivgrouptest.utils.extension.convertPriceToString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(viewModel: FiltersViewModel = hiltViewModel(), actions: MainActions, filters: UIFilters?) {
    LaunchedEffect(Unit) {
        viewModel.getFilters(filters)
    }

    val initialRanges = viewModel.filterRanges.collectAsState().value ?: return
    val currentFilters = viewModel.filters.collectAsState().value ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.filters)) },
                navigationIcon = {
                    IconButton(
                        onClick = { actions.navigateUp() },
                        content = {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = Icons.Filled.Close.name,
                            )
                        },
                    )
                },
                actions = {
                    IconButton(
                        onClick = { actions.applyFilters(null) },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.reset_24),
                                contentDescription = "Reset",
                            )
                        },
                    )

                    IconButton(
                        onClick = { actions.applyFilters(currentFilters) },
                        content = {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = Icons.Filled.Check.name,
                            )
                        },
                    )
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 24.dp),
                content = {
                    item {
                        Text(
                            stringResource(
                                id = R.string.price,
                                currentFilters.minPrice.convertPriceToString(),
                                currentFilters.maxPrice.convertPriceToString(),
                            ),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                    item {
                        RangeSlider(
                            value = currentFilters.minPrice.toFloat()..currentFilters.maxPrice.toFloat(),
                            onValueChange = { range -> viewModel.seekPrice(range.start, range.endInclusive) },
                            valueRange = initialRanges.minPrice.toFloat()..initialRanges.maxPrice.toFloat(),
                        )
                    }
                    item {
                        Text(
                            stringResource(
                                id = R.string.area,
                                currentFilters.minArea.convertAreaToString(),
                                currentFilters.maxArea.convertAreaToString(),
                            ),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                    item {
                        RangeSlider(
                            value = currentFilters.minArea.toFloat()..currentFilters.maxArea.toFloat(),
                            onValueChange = { range -> viewModel.seekArea(range.start, range.endInclusive) },
                            valueRange = initialRanges.minArea.toFloat()..initialRanges.maxArea.toFloat(),
                        )
                    }
                    item {
                        Text(
                            stringResource(
                                id = R.string.rooms,
                                currentFilters.minRooms.toString(),
                                currentFilters.maxRooms.toString(),
                            ),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                    item {
                        RangeSlider(
                            value = currentFilters.minRooms.toFloat()..currentFilters.maxRooms.toFloat(),
                            onValueChange = { range -> viewModel.seekRooms(range.start, range.endInclusive) },
                            valueRange = initialRanges.minRooms.toFloat()..initialRanges.maxRooms.toFloat(),
                        )
                    }
                    item {
                        Text(
                            stringResource(
                                id = R.string.bedrooms,
                                currentFilters.minBedrooms.toString(),
                                currentFilters.maxBedrooms.toString(),
                            ),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                    item {
                        RangeSlider(
                            value = currentFilters.minBedrooms.toFloat()..currentFilters.maxBedrooms.toFloat(),
                            onValueChange = { range -> viewModel.seekBedrooms(range.start, range.endInclusive) },
                            valueRange = initialRanges.minBedrooms.toFloat()..initialRanges.maxBedrooms.toFloat(),
                        )
                    }
                }
            )
        }
    )
}