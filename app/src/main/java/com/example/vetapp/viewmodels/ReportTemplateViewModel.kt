package com.example.vetapp.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import com.example.vetapp.reports.ReportTemplateField
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.ReportTemplateRepository

class ReportTemplateViewModel {
    private val reportTemplateRepository : IReportTemplateRepository = ReportTemplateRepository()

    val reportTemplateFields = reportTemplateRepository.ReportTemplateObservable()

    fun insert(reportTemplateField: List<ReportTemplateField>){

    }
}