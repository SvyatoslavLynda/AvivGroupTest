package com.svdroid.avivgrouptest.data.repository.source.db

import com.svdroid.avivgrouptest.data.db.PropertiesDao
import com.svdroid.avivgrouptest.data.db.PropertiesEntity
import com.svdroid.avivgrouptest.data.mapper.toUIFilters
import com.svdroid.avivgrouptest.data.ui.UIFilters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PropertiesDbDataSourceImpl @Inject constructor(
    private val propertiesDao: PropertiesDao,
) : PropertiesDbDataSource {

    override suspend fun createProperties(properties: List<PropertiesEntity>) {
        withContext(Dispatchers.IO) {
            propertiesDao.deleteProperties()
            propertiesDao.createProperties(properties)
        }
    }

    override fun getProperties(): Flow<List<PropertiesEntity>> {
        return propertiesDao.getProperties()
    }

    override fun getFavoriteProperties(): Flow<List<PropertiesEntity>> {
        return propertiesDao.getFavoriteProperties()
    }

    override fun getProperty(id: Long): Flow<PropertiesEntity?> {
        return propertiesDao.getProperty(id)
    }

    override suspend fun getFiltersRanges(): UIFilters {
        val properties = propertiesDao.getProperties().first()

        return properties.toUIFilters()
    }

    override suspend fun updateProperties(vararg property: PropertiesEntity) {
        withContext(Dispatchers.IO) {
            propertiesDao.updateProperties(propertiesEntity = property)
        }
    }
}