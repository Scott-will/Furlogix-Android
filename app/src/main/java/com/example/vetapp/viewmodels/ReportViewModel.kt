package com.example.vetapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.reports.ReportTemplateField
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.IReportsRepository
import com.example.vetapp.repositories.ReportTemplateRepository
import com.example.vetapp.repositories.ReportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportTemplateRepository : ReportTemplateRepository,
    private val reportRepository : IReportsRepository) : ViewModel()
{
    val reportTemplateFields = reportTemplateRepository.ReportTemplateObservable().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val reports = reportRepository.reportsObservable().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun insert(reportTemplateField: List<ReportTemplateField>, name : String ){
        //insert report
        val report = Reports(Name = name)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                reportRepository.insertReport(report)
            }

        }
        //TODO:insert fields with link to newly inserted report
    }

}