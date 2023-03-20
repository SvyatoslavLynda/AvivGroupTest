package com.svdroid.avivgrouptest.data.repository

import com.svdroid.avivgrouptest.data.ui.UIFilters
import com.svdroid.avivgrouptest.data.ui.UIPropertyModel
import com.svdroid.avivgrouptest.data.utils.DataState
import kotlinx.coroutines.flow.Flow

interface PropertiesRepository {

    fun getProperties(filters: UIFilters?): Flow<DataState<List<UIPropertyModel>>>

    fun getFavoriteProperties(): Flow<DataState<List<UIPropertyModel>>>

    fun getProperty(id: Long): Flow<DataState<UIPropertyModel>>

    suspend fun getFiltersRanges(): UIFilters

    suspend fun toggleFavorite(property: UIPropertyModel): DataState<Unit>
}