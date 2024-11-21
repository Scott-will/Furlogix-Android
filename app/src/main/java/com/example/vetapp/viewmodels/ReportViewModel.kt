package com.example.vetapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.IReportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportTemplateRepository : IReportTemplateRepository,
    private val reportRepository : IReportsRepository) : ViewModel()
{
    val reportTemplateFields = reportTemplateRepository.ReportTemplateObservable().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val reports = reportRepository.reportsObservable().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun insertReport( name : String ){
        //insert report
        val report = Reports(Name = name)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                reportRepository.insertReport(report)
            }

        }
        //TODO:insert fields with link to newly inserted report
    }

    fun insertReportTemplateField(field : ReportTemplateField){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                reportTemplateRepository.insertReportTemplateField(field)
            }
        }
    }
}