package com.example.vetapp.injectionModules

import android.content.Context
import androidx.room.Room
import com.example.vetapp.Database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {

    // Provide the AppDatabase instance
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "reports_database" // Database name
        ).fallbackToDestructiveMigration() // Optional: Handle schema changes
            .build()
    }
}