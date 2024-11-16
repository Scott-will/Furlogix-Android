package com.example.vetapp.injectionModules

import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.ReportTemplateDao
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.ReportTemplateRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ReportTemplateRepositoryModule {

    @Binds
    abstract fun bindReportTemplateRepository(reportsRepository: ReportTemplateRepository): IReportTemplateRepository

    companion object {
        @Provides
        fun provideReportTemplateDao(database: AppDatabase): ReportTemplateDao {
            return database.reportTemplateDao()
        }
    }
}