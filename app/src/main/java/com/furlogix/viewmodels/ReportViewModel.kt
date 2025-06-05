package com.furlogix.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furlogix.Database.DAO.UserDao
import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.Database.Entities.Reports
import com.furlogix.Furlogix
import com.furlogix.email.CsvBuilder
import com.furlogix.email.EmailHandler
import com.furlogix.email.EmailWrapper
import com.furlogix.email.IEmailHandler
import com.furlogix.logger.ILogger
import com.furlogix.repositories.IReportEntryRepository
import com.furlogix.repositories.IReportTemplateRepository
import com.furlogix.repositories.IReportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val logger : ILogger,
    private val reportTemplateRepository : IReportTemplateRepository,
    private val reportRepository : IReportsRepository,
    private val userDao: UserDao,
    private val reportEntryRepository : IReportEntryRepository) : ViewModel() {

    private val TAG = "Furlogix:" + ReportViewModel::class.qualifiedName

    private var _isError = MutableStateFlow<Boolean>(false)
    private var _errorMsg = MutableStateFlow<String>("")
    var isError : StateFlow<Boolean> = _isError
    var errorMsg : StateFlow<String> = _errorMsg

    val reports = reportRepository.reportsObservable()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private var _reportEntries = MutableStateFlow<MutableMap<Int, List<ReportEntry>>>(mutableMapOf())
    val reportEntries: StateFlow<MutableMap<Int, List<ReportEntry>>> = _reportEntries
    private var _isTooManyReports = MutableStateFlow(false)

    var isTooManyReports = _isTooManyReports

    //this is called everytime viewModel is created
    init {
        checkTooManyReportEntries()
    }

    fun getReportNameById(id: Int) = flow {
        reportRepository.getReportByIdFlow(id).collect { result ->
            emit(result.name)
        }
    }

    fun insertReport(name: String, petId : Int) {
        //insert report
        val report = Reports(name = name, petId = petId)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                logger.log(TAG, "Inserting report ${report.Id}, ${report.name}")
                val result = reportRepository.insertReport(report)
                UpdateErrorState(!result.result, result.msg)
                logger.log(
                    TAG,
                    "Result of inserting report ${report.Id}, ${report.name} : ${result.result}"
                )
            }

        }
    }

    fun DeleteSentReports() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                reportEntryRepository.deleteSentReportEntries()
            }
        }
    }
    fun deleteReport(report: Reports) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                logger.log(TAG, "Deleting report ${report.Id}, ${report.name}")
                reportRepository.deleteReport(report)
            }

        }
    }

    fun updateReport(report: Reports) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                logger.log(TAG, "Updating report ${report.Id}, ${report.name}")
                val result = reportRepository.updateReport(report)
                UpdateErrorState(!result.result, result.msg)
                logger.log(
                    TAG,
                    "Result of updating report ${report.Id}, ${report.name} : ${result.result}"
                )
            }

        }
    }

    fun gatherReportData(reportId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                logger.log(TAG, "Gathering report data to send in email")
                val reportName = reportRepository.getReportById(reportId).name
                val templates = reportTemplateRepository.GetReportById(reportId)
                val entries = reportEntryRepository.getAllReportEntriesById(reportId)
                var fileUri = CsvBuilder().buildAndWriteCsv(
                    Furlogix.applicationContext(),
                    reportName,
                    entries,
                    templates
                )
                val email = userDao.getCurrentUserEmailInLine()
                //TODO: Add pet name here
                val emailWrapper =
                    EmailWrapper(email, "Pet Reports", "${reportName}_${Date()}", fileUri)
                SendEmail(emailWrapper)
                entries.forEach() { x ->
                    x.sent = true
                }
                reportEntryRepository.updateReportEntries(entries)
            }

        }
    }

    fun SendEmail(wrapper: EmailWrapper) {
        val emailHandler: IEmailHandler = EmailHandler(Furlogix.applicationContext(), userDao)
        logger.log(TAG, "Starting process of sending email")
        emailHandler.CreateAndSendEmail(wrapper)
        logger.log(TAG, "finished process of sending email")
    }

    fun UpdateErrorState(isError: Boolean, errorMsg: String) {
        this._isError.value = isError
        this._errorMsg.value = errorMsg

    }

    private fun checkTooManyReportEntries() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isTooManyReports.value = isTooManyReportEntries()
            }

        }
    }

    private suspend fun isTooManyReportEntries(): Boolean {
        val size = reportEntryRepository.getSizeOfReportEntryTableKB() // suspend function
        return size > 100
    }

    fun PopulateReportEntriesProperty(reportTemplateId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                logger.log("ReportViewModel", "getting entries for ${reportTemplateId}")
                reportEntryRepository.getAllReportEntriesForTemplate(reportTemplateId)
                    .collect { entries ->
                        logger.log("ReportViewModel", "got an entry for ${reportTemplateId}")
                        val updatedMap = _reportEntries.value.toMutableMap()
                        updatedMap[reportTemplateId] = entries
                        _reportEntries.value = updatedMap.toMutableMap()
                        logger.log("ReportViewModel", reportEntries.value.size.toString() )
                    }
            }
        }
    }
}