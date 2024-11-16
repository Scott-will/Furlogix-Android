package com.example.vetapp.repositories

import androidx.lifecycle.LiveData
import com.example.vetapp.Database.Entities.Reports
import kotlinx.coroutines.flow.Flow

interface IReportsRepository {

    fun reportsObservable() : Flow<List<Reports>>

    fun insertReport(report : Reports)

}