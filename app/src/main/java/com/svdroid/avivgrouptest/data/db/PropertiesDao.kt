package com.svdroid.avivgrouptest.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertiesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createProperties(properties: List<PropertiesEntity>)

    @Query("SELECT * FROM PropertiesEntity")
    fun getProperties(): Flow<List<PropertiesEntity>>

    @Query("SELECT * FROM PropertiesEntity WHERE is_favorite=1")
    fun getFavoriteProperties(): Flow<List<PropertiesEntity>>

    @Query("SELECT * FROM PropertiesEntity WHERE id=:id")
    fun getProperty(id: Long): Flow<PropertiesEntity>

    @Update
    fun updateProperties(vararg propertiesEntity: PropertiesEntity)

    @Query("DELETE FROM PropertiesEntity")
    fun deleteProperties()
}