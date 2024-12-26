package com.example.vetapp.di

import ReportCleanerWorker
import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.vetapp.repositories.IReportEntryRepository
import javax.inject.Inject

class CustomHiltWorkerFactory @Inject constructor(
   private val reportEntryRepository: IReportEntryRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = ReportCleanerWorker(appContext, workerParameters, reportEntryRepository)
}