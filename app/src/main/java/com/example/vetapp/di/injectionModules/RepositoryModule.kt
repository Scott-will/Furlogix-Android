package com.example.vetapp.di.injectionModules

import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.ReportTemplateDao
import com.example.vetapp.Database.DAO.ReportsDao
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.IReportsRepository
import com.example.vetapp.repositories.ReportTemplateRepository
import com.example.vetapp.repositories.ReportsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // Bind ReportsRepository interface to its implementation
    @Binds
    abstract fun bindReportsRepository(reportsRepository: ReportsRepository): IReportsRepository

    companion object {
        @Provides
        fun provideReportsDao(database: AppDatabase): ReportsDao {
            return database.reportsDao()
        }

        @Provides
        fun provideReportTemplateDao(database: AppDatabase): ReportTemplateDao {
            return database.reportTemplateDao()
        }
    }

    @Binds
    abstract fun bindReportTemplateRepository(reportsRepository: ReportTemplateRepository): IReportTemplateRepository
}