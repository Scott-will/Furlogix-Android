package com.furlogix.di.injectionModules

import com.furlogix.logger.ILogger
import com.furlogix.logger.LogcatLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LoggerModule {
    @Binds
    abstract fun bindReportsRepository(logger: LogcatLogger): ILogger
}