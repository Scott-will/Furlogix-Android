package com.example.vetapp.di.injectionModules

import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.ReportEntryDao
import com.example.vetapp.Database.DAO.ReportTemplateDao
import com.example.vetapp.Database.DAO.ReportsDao
import com.example.vetapp.email.EmailHandler
import com.example.vetapp.email.IEmailHandler
import com.example.vetapp.repositories.IReportEntryRepository
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.IReportsRepository
import com.example.vetapp.repositories.ReportEntryRepository
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

    // repository binding
    @Binds
    abstract fun bindReportsRepository(reportsRepository: ReportsRepository): IReportsRepository

    @Binds
    abstract fun bindReportTemplateRepository(reportsRepository: ReportTemplateRepository): IReportTemplateRepository

    @Binds
    abstract fun bindReportEntryRepository(reportsRepository: ReportEntryRepository): IReportEntryRepository

    companion object {
        @Provides
        fun provideReportsDao(database: AppDatabase): ReportsDao {
            return database.reportsDao()
        }

        @Provides
        fun provideReportTemplateDao(database: AppDatabase): ReportTemplateDao {
            return database.reportTemplateDao()
        }

        @Provides
        fun provideReportEntryDao(database: AppDatabase): ReportEntryDao {
            return database.reportEntryDao()
        }
    }
}