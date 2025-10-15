package com.example.weatherappcompose.di

import android.content.Context
import com.example.weatherappcompose.data.datasource.WeatherRemoteDataSource
import com.example.weatherappcompose.data.repository.WeatherRepositoryImpl
import com.example.weatherappcompose.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideWeatherRemoteDataSource(
        @ApplicationContext context: Context
    ): WeatherRemoteDataSource {
        return WeatherRemoteDataSource(context)
    }
    
    @Provides
    @Singleton
    fun provideWeatherRepository(
        remoteDataSource: WeatherRemoteDataSource
    ): WeatherRepository {
        return WeatherRepositoryImpl(remoteDataSource)
    }
}
