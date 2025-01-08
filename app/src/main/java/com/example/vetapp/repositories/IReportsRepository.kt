package com.example.vetapp.repositories

import com.example.vetapp.Database.Entities.Reports
import kotlinx.coroutines.flow.Flow

interface IReportsRepository {

    fun reportsObservable() : Flow<List<Reports>>

    suspend fun getAllReports() : List<Reports>

    fun getReportByIdFlow(id : Int): Flow<Reports>

    suspend fun getReportById(id: Int) : Reports

    suspend fun insertReport(report : Reports) : com.example.vetapp.Result

    suspend fun updateReport(report : Reports) : com.example.vetapp.Result

    suspend fun deleteReport(report : Reports)

}