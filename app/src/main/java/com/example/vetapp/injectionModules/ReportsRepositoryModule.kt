package com.example.vetapp.injectionModules

import androidx.lifecycle.LiveData
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.ReportsDao
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.repositories.IReportsRepository
import com.example.vetapp.repositories.ReportsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReportsRepositoryModule {

    // Bind ReportsRepository interface to its implementation
    @Binds
    abstract fun bindReportsRepository(reportsRepository: ReportsRepository): IReportsRepository

    companion object {
        @Provides
        fun provideReportsDao(database: AppDatabase): ReportsDao {
            return database.reportsDao()
        }
    }
}