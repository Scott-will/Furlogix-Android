package com.furlogix.di.injectionModules

import com.furlogix.Database.AppDatabase
import com.furlogix.Database.DAO.PetDao
import com.furlogix.Database.DAO.RemindersDao
import com.furlogix.Database.DAO.ReportEntryDao
import com.furlogix.Database.DAO.ReportTemplateDao
import com.furlogix.Database.DAO.ReportsDao
import com.furlogix.Database.DAO.UserDao
import com.furlogix.reports.ReportTemplateValidator
import com.furlogix.reports.ReportValidator
import com.furlogix.repositories.IPetRepository
import com.furlogix.repositories.IRemindersRepository
import com.furlogix.repositories.IReportEntryRepository
import com.furlogix.repositories.IReportTemplateRepository
import com.furlogix.repositories.IReportsRepository
import com.furlogix.repositories.IUserRepository
import com.furlogix.repositories.PetRepository
import com.furlogix.repositories.RemindersRepository
import com.furlogix.repositories.ReportEntryRepository
import com.furlogix.repositories.ReportTemplateRepository
import com.furlogix.repositories.ReportsRepository
import com.furlogix.repositories.UserRepository
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

    @Binds
    abstract fun bindReminderRepository(reminderRepository: RemindersRepository): IRemindersRepository

    @Binds
    abstract fun bindPetRepository(petRepository: PetRepository): IPetRepository

    @Binds
    abstract fun bindUserRepository(userRepository: UserRepository): IUserRepository


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

        @Provides
        fun provideUserDao(database: AppDatabase): UserDao {
            return database.userDao()
        }

        @Provides
        fun providePetDao(database: AppDatabase): PetDao {
            return database.petDao()
        }

        @Provides
        fun provideReminderDao(database: AppDatabase): RemindersDao {
            return database.remindersDao()
        }

        @Provides
        fun provideReportValidator() : ReportValidator{
            return ReportValidator()
        }

        @Provides
        fun provideReportTemplateValidator() : ReportTemplateValidator{
            return ReportTemplateValidator()
        }
    }
}