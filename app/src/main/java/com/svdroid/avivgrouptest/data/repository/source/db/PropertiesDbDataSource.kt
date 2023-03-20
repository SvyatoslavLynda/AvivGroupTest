package com.svdroid.avivgrouptest.data.repository.source.db

import com.svdroid.avivgrouptest.data.db.PropertiesEntity
import com.svdroid.avivgrouptest.data.ui.UIFilters
import kotlinx.coroutines.flow.Flow

interface PropertiesDbDataSource {

    suspend fun createProperties(properties: List<PropertiesEntity>)

    fun getProperties(): Flow<List<PropertiesEntity>>

    fun getFavoriteProperties(): Flow<List<PropertiesEntity>>

    fun getProperty(id: Long): Flow<PropertiesEntity?>

    suspend fun getFiltersRanges(): UIFilters

    suspend fun updateProperties(vararg property: PropertiesEntity)
}