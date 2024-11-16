package com.example.vetapp.repositories

import android.database.Observable
import androidx.lifecycle.LiveData
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.injectionModules.ReportsRepositoryModule
import dagger.Component
import kotlinx.coroutines.flow.Flow

interface IReportTemplateRepository {
    fun ReportTemplateObservable() : Flow<List<ReportTemplateField>>

    fun insertReportTemplateFields(reportTemplateFields : List<ReportTemplateField>)
    
    fun GetReportById(id : Int) : Flow<List<ReportTemplateField>>
}