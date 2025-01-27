package com.amb.stockmanagerapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IODispatcher

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @Provides
    @IODispatcher
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}