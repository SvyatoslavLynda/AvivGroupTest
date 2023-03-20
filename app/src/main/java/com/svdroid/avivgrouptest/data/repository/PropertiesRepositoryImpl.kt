package com.svdroid.avivgrouptest.data.repository

import com.svdroid.avivgrouptest.data.mapper.toDB
import com.svdroid.avivgrouptest.data.mapper.toUI
import com.svdroid.avivgrouptest.data.repository.source.db.PropertiesDbDataSource
import com.svdroid.avivgrouptest.data.repository.source.network.PropertiesNetworkSource
import com.svdroid.avivgrouptest.data.ui.UIFilters
import com.svdroid.avivgrouptest.data.ui.UIPropertyModel
import com.svdroid.avivgrouptest.data.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.sql.SQLException
import javax.inject.Inject

class PropertiesRepositoryImpl @Inject constructor(
    private val dbDataSource: PropertiesDbDataSource,
    private val networkDataSource: PropertiesNetworkSource,
) : PropertiesRepository {
    private var filters: UIFilters? = null

    override fun getProperties(filters: UIFilters?): Flow<DataState<List<UIPropertyModel>>> = flow {
        this@PropertiesRepositoryImpl.filters = filters

        try {
            emit(DataState.Loading())

            val propertiesFlow = dbDataSource.getProperties()
            var localPropertyItems: List<UIPropertyModel> = propertiesFlow.first().map { it.toUI() }

            try {
                val response = networkDataSource.getPropertiesList()
                val entities = response.items.map { remotePropertyItem ->
                    val localItem = localPropertyItems.firstOrNull { localPropertyItem ->
                        remotePropertyItem.id == localPropertyItem.id
                    }

                    remotePropertyItem.toDB(isFavorite = localItem?.isFavorite ?: false)
                }

                dbDataSource.createProperties(entities)
            } catch (_: Exception) {
            }

            propertiesFlow.collect { list ->
                val currentFilters = this@PropertiesRepositoryImpl.filters
                localPropertyItems = (if (currentFilters != null) list.filter { model ->
                    val price = (model.price ?: 0.0) in currentFilters.minPrice..currentFilters.maxPrice
                    val area = (model.area ?: 0.0) in currentFilters.minArea..currentFilters.maxArea
                    val rooms = (model.rooms ?: 0) in currentFilters.minRooms..currentFilters.maxRooms
                    val bedrooms = (model.bedrooms ?: 0) in currentFilters.minBedrooms..currentFilters.maxBedrooms

                    return@filter price && area && rooms && bedrooms
                } else list).map { it.toUI() }
                emit(DataState.Data(localPropertyItems))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Something went wrong!"))
        }
    }

    override fun getFavoriteProperties(): Flow<DataState<List<UIPropertyModel>>> = flow {
        try {
            emit(DataState.Loading())

            dbDataSource.getFavoriteProperties().collect { dataFlow ->
                emit(DataState.Data(dataFlow.map { it.toUI() }))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Something went wrong!"))
        }
    }

    override fun getProperty(id: Long): Flow<DataState<UIPropertyModel>> = flow {
        try {
            emit(DataState.Loading())
            val propertyFlow = dbDataSource.getProperty(id)
            val localProperty = propertyFlow.first()

            try {
                val response = networkDataSource.getProperty(id)
                val entry = response.toDB(isFavorite = localProperty?.isFavorite ?: false)
                dbDataSource.updateProperties(entry)
            } catch (_: Exception) {
            }

            propertyFlow.collect { dataFlow ->
                if (dataFlow == null) {
                    emit(DataState.Error("Something went wrong! Can't find this properties.."))
                } else {
                    emit(DataState.Data(dataFlow.toUI()))
                }
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Something went wrong!"))
        }
    }

    override suspend fun getFiltersRanges(): UIFilters {
        return dbDataSource.getFiltersRanges()
    }

    override suspend fun toggleFavorite(property: UIPropertyModel): DataState<Unit> {
        val entity = dbDataSource.getProperty(property.id).first()

        return try {
            if (entity != null) {
                dbDataSource.updateProperties(entity.copy(isFavorite = !property.isFavorite))

                DataState.Data(Unit)
            } else {
                DataState.Error("Song not found")
            }
        } catch (e: SQLException) {
            DataState.Error("Error occurred updating song ${e.message}")
        }
    }
}