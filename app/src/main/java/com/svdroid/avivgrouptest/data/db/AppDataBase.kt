package com.svdroid.avivgrouptest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PropertiesEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getPropertiesDao(): PropertiesDao
}