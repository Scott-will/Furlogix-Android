package com.example.vetapp.viewmodels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vetapp.Database.DAO.UserDao
import com.example.vetapp.Database.Entities.ReportEntry
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.VetApplication
import com.example.vetapp.email.CsvBuilder
import com.example.vetapp.email.EmailHandler
import com.example.vetapp.email.EmailWrapper
import com.example.vetapp.email.IEmailHandler
import com.example.vetapp.repositories.IReportEntryRepository
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.IReportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportTemplateRepository : IReportTemplateRepository,
    private val reportRepository : IReportsRepository,
    private val userDao: UserDao,
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

    fun gatherReportData(reportId : Int){
        /*get all information from database
        *information is:
        * report name
        * templates
        * data entry
        *
        * once all data is retrieved
        * send to csvBuilder and generate csv file
        *
        * send to email handler as file attachment */
        var reportName : String = ""
        var templates = mutableListOf<ReportTemplateField>()
        var entries = mutableListOf<ReportEntry>()
        viewModelScope.launch {
            reportRepository.getReportById(reportId).collect{result -> reportName = result.name}
            reportTemplateRepository.ReportTemplateObservable().collect{template -> templates.addAll(template)}
            reportEntryRepository.getAllEntriesForReport(reportId).collect{entry -> entries.add(entry)}
            var fileUri = CsvBuilder().buildCsv(VetApplication.applicationContext(), reportName, entries, templates)
            val userEmail = userDao.getCurrentUserEmail()
            val emailWrapper = EmailWrapper(userEmail.value.toString(), userEmail.value.toString(), "${reportName}_${Date()}", "", fileUri.toString())
            SendEmail(emailWrapper)
        }


    }

    fun SendEmail(wrapper : EmailWrapper){
        val emailHandler: IEmailHandler = EmailHandler(VetApplication.applicationContext())
        emailHandler.CreateAndSendEmail(wrapper)
    }
}