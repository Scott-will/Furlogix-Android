package com.example.vetapp.repositories

import androidx.lifecycle.LiveData
import com.example.vetapp.Database.Entities.Reports
import kotlinx.coroutines.flow.Flow

interface IReportsRepository {

    fun reportsObservable() : Flow<List<Reports>>

    suspend fun getAllReports() : List<Reports>

    fun getReportByIdFlow(id : Int): Flow<Reports>

    suspend fun getReportById(id: Int) : Reports

    suspend fun insertReport(report : Reports)

    suspend fun updateReport(report : Reports)

    suspend fun deleteReport(report : Reports)

}