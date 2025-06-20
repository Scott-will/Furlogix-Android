package com.furlogix.di.injectionModules
import com.furlogix.Database.AppDatabase
import com.furlogix.reminders.RequestCodeFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemindersModule {
    @Provides
    @Singleton
    fun provideRequestCodeFactory(db: AppDatabase): RequestCodeFactory {
        return RequestCodeFactory(db)
    }
}
