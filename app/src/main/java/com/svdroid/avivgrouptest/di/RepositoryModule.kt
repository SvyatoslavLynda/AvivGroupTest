package com.svdroid.avivgrouptest.di

import com.svdroid.avivgrouptest.data.repository.PropertiesRepository
import com.svdroid.avivgrouptest.data.repository.PropertiesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsSongRepository(repository: PropertiesRepositoryImpl): PropertiesRepository
}