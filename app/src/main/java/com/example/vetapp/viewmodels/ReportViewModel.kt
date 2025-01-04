package com.example.vetapp.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.Observable
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
    private var _isError = MutableStateFlow<Boolean>(false)
    private var _errorMsg = MutableStateFlow<String>("")
    private var _isTooManyReports = MutableStateFlow(false)
    var isError : StateFlow<Boolean> = _isError
    var errorMsg : StateFlow<String> = _errorMsg
    var isTooManyReports = _isTooManyReports

    //this is called everytime viewModel is created
    init{
        checkTooManyReportEntries()
    }
    fun getReportNameById(id : Int) = flow {
        var report = reportRepository.getReportByIdFlow(id).collect{
            result -> emit(result.name)
        }
    }

    fun insertReport( name : String ){
        //insert report
        val report = Reports(name = name)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = reportRepository.insertReport(report)
                UpdateErrorState(!result.result, result.msg)
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
                val result = reportRepository.updateReport(report)
                UpdateErrorState(!result.result, result.msg)
            }

        }
    }

    fun insertReportTemplateField(field : ReportTemplateField){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = reportTemplateRepository.insertReportTemplateField(field)
                UpdateErrorState(!result.result, result.msg)
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
                val result = reportTemplateRepository.updateReportTemplateField(field)
                UpdateErrorState(!result.result, result.msg)
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
               var result = reportEntryRepository.insertEntries(entries)
               UpdateErrorState(!result.result, result.msg)
           }
       }
    }

    fun gatherReportData(reportId : Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val reportName = reportRepository.getReportById(reportId).name
                val templates = reportTemplateRepository.GetReportById(reportId)
                val entries = reportEntryRepository.getAllReportEntriesById(reportId)
                var fileUri = CsvBuilder().buildAndWriteCsv(VetApplication.applicationContext(), reportName, entries, templates)
                val user = userDao.getUserById(1)
                //TODO: Add pet name here
                val emailWrapper = EmailWrapper(user?.email!!, "Pet Reports", "${reportName}_${Date()}", fileUri)
                SendEmail(emailWrapper)
                entries.forEach(){
                    x ->
                    x.sent = true
                }
                reportEntryRepository.updateReportEntries(entries)
            }

        }
    }

    fun SendEmail(wrapper : EmailWrapper){
        val emailHandler: IEmailHandler = EmailHandler(VetApplication.applicationContext(), userDao)
        emailHandler.CreateAndSendEmail(wrapper)
    }

    fun UpdateErrorState(isError : Boolean, errorMsg : String){
        this._isError.value = isError
        this._errorMsg.value = errorMsg

    }

    private fun checkTooManyReportEntries() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _isTooManyReports.value = isTooManyReportEntries()
            }

        }
    }

    private suspend fun isTooManyReportEntries(): Boolean {
        val size = reportEntryRepository.getSizeOfReportEntryTableKB() // suspend function
        return size > 100
    }

    public fun DeleteSentReports(){
        viewModelScope.launch { withContext(Dispatchers.IO){
            reportEntryRepository.deleteSentReportEntries()
            }
        }

    }
}