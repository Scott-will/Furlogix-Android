package com.furlogix.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furlogix.Database.DAO.UserDao
import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.Database.Entities.Reports
import com.furlogix.Furlogix
import com.furlogix.email.CsvBuilder
import com.furlogix.email.EmailHandler
import com.furlogix.email.EmailWrapper
import com.furlogix.email.IEmailHandler
import com.furlogix.reports.FieldType
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
    private val reportTemplateRepository : IReportTemplateRepository,
    private val reportRepository : IReportsRepository,
    private val userDao: UserDao,
    private val reportEntryRepository : IReportEntryRepository) : ViewModel() {

    val reportTemplateFields = reportTemplateRepository.ReportTemplateObservable()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val reports = reportRepository.reportsObservable()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private var _reportsForPet = MutableStateFlow<List<Reports>>(emptyList())
    val reportsForPet : MutableStateFlow<List<Reports>> = _reportsForPet

    private var _currentReportTemplate = MutableStateFlow(ReportTemplateField(0, 0, FieldType.TEXT, ""))
    val currentReportTemplate : MutableStateFlow<ReportTemplateField> = _currentReportTemplate

    private var _reportTemplatesForCurrentReport = MutableStateFlow<List<ReportTemplateField>>(
        emptyList()
    )
    val reportTemplatesForCurrentReport : MutableStateFlow<List<ReportTemplateField>> = _reportTemplatesForCurrentReport

    private var _reportEntries = MutableStateFlow<MutableMap<Int, List<ReportEntry>>>(mutableMapOf())
    val reportEntries: StateFlow<MutableMap<Int, List<ReportEntry>>> = _reportEntries
    private var _isError = MutableStateFlow<Boolean>(false)
    private var _errorMsg = MutableStateFlow<String>("")
    private var _isTooManyReports = MutableStateFlow(false)

    private var _favouriteReportTemplates = MutableStateFlow<List<ReportTemplateField>>(emptyList())
    val favouriteReportTemplates : StateFlow<List<ReportTemplateField>> = _favouriteReportTemplates

    private var _favouriteReportTemplatesData = MutableStateFlow<MutableMap<Int, List<ReportEntry>>>(mutableMapOf())
    val favouriteReportTemplatesData : StateFlow<Map<Int, List<ReportEntry>>> = _favouriteReportTemplatesData

    var isError : StateFlow<Boolean> = _isError
    var errorMsg : StateFlow<String> = _errorMsg

    private val TAG = "Furlogix:" + ReportViewModel::class.qualifiedName

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

    fun populateReportForPet(petId : Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _reportsForPet.value =
                    reportRepository.reportsForPetObservable(petId)
            }
        }
    }

    fun populateCurrentReportTemplate(reportTemplateId : Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _currentReportTemplate.value =
                    reportTemplateRepository.GetTemplateById(reportTemplateId)
            }
        }
    }

    fun populateReportTemplatesForCurrentReport(reportId : Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _reportTemplatesForCurrentReport.value =
                    reportTemplateRepository.GetReportById(reportId)
            }
        }
    }

    fun insertReport(name: String, petId : Int) {
        //insert report
        val report = Reports(name = name, petId = petId)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d(TAG, "Inserting report ${report.Id}, ${report.name}")
                val result = reportRepository.insertReport(report)
                UpdateErrorState(!result.result, result.msg)
                Log.d(
                    TAG,
                    "Result of inserting report ${report.Id}, ${report.name} : ${result.result}"
                )
            }

        }
    }

    fun deleteReport(report: Reports) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d(TAG, "Deleting report ${report.Id}, ${report.name}")
                reportRepository.deleteReport(report)
            }

        }
    }

    fun updateReport(report: Reports) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d(TAG, "Updating report ${report.Id}, ${report.name}")
                val result = reportRepository.updateReport(report)
                UpdateErrorState(!result.result, result.msg)
                Log.d(
                    TAG,
                    "Result of updating report ${report.Id}, ${report.name} : ${result.result}"
                )
            }

        }
    }

    fun insertReportTemplateField(field: ReportTemplateField) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d(TAG, "Inserting report template field ${field.uid}, ${field.name}")
                val result = reportTemplateRepository.insertReportTemplateField(field)
                UpdateErrorState(!result.result, result.msg)
                Log.d(
                    TAG,
                    "Result of inserting report template field ${field.uid}, ${field.name} : ${result.result}"
                )
            }
        }
    }

    fun deleteReportTemplateField(field: ReportTemplateField) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d(TAG, "Deleting report template field ${field.uid}, ${field.name}")
                reportTemplateRepository.deleteReportTemplateField(field)
            }
        }
    }

    fun updateReportTemplateField(field: ReportTemplateField) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d(TAG, "Updating report template field ${field.uid}, ${field.name}")
                val result = reportTemplateRepository.updateReportTemplateField(field)
                UpdateErrorState(!result.result, result.msg)
                Log.d(
                    TAG,
                    "Result of updating report template field ${field.uid}, ${field.name} : ${result.result}"
                )
            }
        }
    }

    fun insertReportEntry(valueMap: Map<Int, MutableState<String>>, reportId: Int) {
        var entries = mutableListOf<ReportEntry>()
        val time = Date()
        valueMap.forEach() { kvp ->
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
                Log.d(TAG, "Inserting ${entries.count()} report entries")
                var result = reportEntryRepository.insertEntries(entries)
                Log.d(
                    TAG,
                    "Result of inserting ${entries.count()} report entries : ${result.result}"
                )
                UpdateErrorState(!result.result, result.msg)
            }
        }
    }

    fun gatherReportData(reportId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d(TAG, "Gathering report data to send in email")
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
        Log.d(TAG, "Starting process of sending email")
        emailHandler.CreateAndSendEmail(wrapper)
        Log.d(TAG, "finished process of sending email")
    }

    fun UpdateErrorState(isError: Boolean, errorMsg: String) {
        this._isError.value = isError
        this._errorMsg.value = errorMsg

    }

    fun updateFavouriteReportTemplateItem(reportTemplateId : Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                reportTemplateRepository.flipFavouriteReportTemplateField(reportTemplateId)
            }
        }
    }

    fun PopulateFavouriteReportTemplates(petId : Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _favouriteReportTemplates.value =
                    reportTemplateRepository.GetFavouriteReportTemplatesForPet(petId)
            }
        }
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

    fun DeleteSentReports() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                reportEntryRepository.deleteSentReportEntries()
            }
        }
    }

    fun PopulateFavouriteReportTemplateData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (template in favouriteReportTemplates.value) {
                    Log.d("ReportViewModel", "Populating fav template data")
                    _favouriteReportTemplatesData.value[template.uid] =
                        reportEntryRepository.getAllEntriesForReportTemplate(template.uid)
                    Log.d("ReportViewModel", "populated for ${template.name}")
                }
            }
        }
    }

    fun PopulateReportEntriesProperty(reportTemplateId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d("ReportViewModel", "getting entries for ${reportTemplateId}")
                reportEntryRepository.getAllReportEntriesForTemplate(reportTemplateId)
                    .collect { entries ->
                        Log.d("ReportViewModel", "got an entry for ${reportTemplateId}")
                        val updatedMap = _reportEntries.value.toMutableMap()
                        updatedMap[reportTemplateId] = entries
                        _reportEntries.value = updatedMap.toMutableMap()
                        Log.d("ReportViewModel", reportEntries.value.size.toString() )
                    }
            }
        }
    }
}