package com.furlogix.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furlogix.Database.DAO.UserDao
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.reports.FieldType
import com.furlogix.repositories.IReportEntryRepository
import com.furlogix.repositories.IReportTemplateRepository
import com.furlogix.repositories.IReportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReportTemplatesViewModels @Inject constructor(
    private val reportTemplateRepository : IReportTemplateRepository,
    private val reportRepository : IReportsRepository,
    private val userDao: UserDao,
    private val reportEntryRepository : IReportEntryRepository
) : ViewModel() {

    private val TAG = "Furlogix:" + ReportTemplatesViewModels::class.qualifiedName
    //Error handling variables
    private var _isError = MutableStateFlow<Boolean>(false)
    private var _errorMsg = MutableStateFlow<String>("")
    var isError : StateFlow<Boolean> = _isError
    var errorMsg : StateFlow<String> = _errorMsg

    val reportTemplateFields = reportTemplateRepository.ReportTemplateObservable()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private var _currentReportTemplate = MutableStateFlow(ReportTemplateField(0, 0, FieldType.TEXT, "paw",false, "paw"))
    val currentReportTemplate : MutableStateFlow<ReportTemplateField> = _currentReportTemplate

    private var _reportTemplatesForCurrentReport = MutableStateFlow<List<ReportTemplateField>>(
        emptyList()
    )
    val reportTemplatesForCurrentReport : MutableStateFlow<List<ReportTemplateField>> = _reportTemplatesForCurrentReport


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

    fun UpdateErrorState(isError: Boolean, errorMsg: String) {
        this._isError.value = isError
        this._errorMsg.value = errorMsg

    }

}