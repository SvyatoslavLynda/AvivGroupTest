package com.svdroid.avivgrouptest.di

import com.svdroid.avivgrouptest.data.repository.source.db.PropertiesDbDataSource
import com.svdroid.avivgrouptest.data.repository.source.db.PropertiesDbDataSourceImpl
import com.svdroid.avivgrouptest.data.repository.source.network.PropertiesNetworkSource
import com.svdroid.avivgrouptest.data.repository.source.network.PropertiesNetworkSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourcesModule {

    @Binds
    abstract fun bindsNetworkDataSource(networkDataSource: PropertiesNetworkSourceImpl): PropertiesNetworkSource

    @Binds
    abstract fun bindsDbDataSource(dbDataSource: PropertiesDbDataSourceImpl): PropertiesDbDataSource
}