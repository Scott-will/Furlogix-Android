package com.example.vetapp.viewmodels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vetapp.Database.Entities.ReportEntry
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.repositories.IReportEntryRepository
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.IReportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportTemplateRepository : IReportTemplateRepository,
    private val reportRepository : IReportsRepository,
    private val reportEntryRepository : IReportEntryRepository) : ViewModel()
{
    val reportTemplateFields = reportTemplateRepository.ReportTemplateObservable().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val reports = reportRepository.reportsObservable().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getReportNameById(id : Int) = flow {
        var report = reportRepository.getReportById(id).collect{
            result -> emit(result.name)
        }
    }

    fun insertReport( name : String ){
        //insert report
        val report = Reports(name = name)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                reportRepository.insertReport(report)
            }

        }
    }

    fun deleteReport( report : Reports ){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                reportRepository.deleteReport(report)
            }

        }
    }

    fun updateReport( report : Reports ){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                reportRepository.updateReport(report)
            }

        }
    }

    fun insertReportTemplateField(field : ReportTemplateField){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                reportTemplateRepository.insertReportTemplateField(field)
            }
        }
    }

    fun deleteReportTemplateField(field : ReportTemplateField){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                reportTemplateRepository.deleteReportTemplateField(field)
            }
        }
    }

    fun updateReportTemplateField(field : ReportTemplateField){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                reportTemplateRepository.updateReportTemplateField(field)
            }
        }
    }

    fun insertReportEntry(valueMap : Map<Int, MutableState<String>>, reportId : Int){
        var entries = mutableListOf<ReportEntry>()
        val time = Date()
        valueMap.forEach(){ kvp ->
            val entry = ReportEntry(
                value = kvp.value.value,
                reportId = reportId,
                templateId = kvp.key,
                timestamp = time.toString()
            )
            entries.add(entry)
        }
       viewModelScope.launch {
           withContext(Dispatchers.IO) {
               reportEntryRepository.insertEntries(entries)
           }
       }
    }
}