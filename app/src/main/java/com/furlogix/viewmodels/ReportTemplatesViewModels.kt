package com.furlogix.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.logger.ILogger
import com.furlogix.reports.FieldType
import com.furlogix.repositories.IReportTemplateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReportTemplatesViewModels @Inject constructor(private val logger : ILogger,
                                                    private val reportTemplateRepository : IReportTemplateRepository,
    private val ioDispatcher: CoroutineDispatcher
    ) : ViewModel() {

    private val TAG = "Furlogix:" + ReportTemplatesViewModels::class.qualifiedName
    //Error handling variables
    private var _isError = MutableStateFlow<Boolean>(false)
    private var _errorMsg = MutableStateFlow<String>("")
    var isError : StateFlow<Boolean> = _isError
    var errorMsg : StateFlow<String> = _errorMsg

    val reportTemplateFields = reportTemplateRepository.ReportTemplateObservable()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private var _currentReportTemplate = MutableStateFlow(ReportTemplateField(0, 0, FieldType.TEXT, "paw", "paw", ""))
    val currentReportTemplate : MutableStateFlow<ReportTemplateField> = _currentReportTemplate

    private var _reportTemplatesForCurrentReport = MutableStateFlow<List<ReportTemplateField>>(
        emptyList()
    )
    val reportTemplatesForCurrentReport : MutableStateFlow<List<ReportTemplateField>> = _reportTemplatesForCurrentReport

    fun populateCurrentReportTemplate(reportTemplateId : Int) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                try{
                    _currentReportTemplate.value =
                        reportTemplateRepository.GetTemplateById(reportTemplateId)
                }
                catch(e : Exception){
                    UpdateErrorState(true, "Failed to get current template ${e.message}")
                    logger.logError(TAG, "Failed to get current template ${e.message}", e)
                }
            }
        }
    }

    fun populateReportTemplatesForCurrentReport(reportId : Int) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                try{
                    _reportTemplatesForCurrentReport.value =
                        reportTemplateRepository.GetReportById(reportId)
                }
                catch(e : Exception){
                    UpdateErrorState(true, "Failed to get current templates ${e.message}")
                    logger.logError(TAG, "Failed to get current templates ${e.message}", e)
                }
            }
        }
    }

    fun insertReportTemplateField(field: ReportTemplateField) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                logger.log(TAG, "Inserting report template field ${field.uid}, ${field.name}")
                try{
                    val result = reportTemplateRepository.insertReportTemplateField(field)
                    UpdateErrorState(!result.result, result.msg)
                    logger.log(
                        TAG,
                        "Result of inserting report template field ${field.uid}, ${field.name} : ${result.result}"
                    )
                }
                catch(e : Exception){
                    UpdateErrorState(true, "Failed to insert report template ${e.message}")
                    logger.logError(TAG, "Failed to insert report template ${e.message}", e)
                }
            }
        }
    }

    fun deleteReportTemplateField(field: ReportTemplateField) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                logger.log(TAG, "Deleting report template field ${field.uid}, ${field.name}")
                try{
                    reportTemplateRepository.deleteReportTemplateField(field)
                }
                catch(e : Exception){
                    UpdateErrorState(true, "Failed to delete report template ${e.message}")
                    logger.logError(TAG, "Failed to delete report template ${e.message}", e)
                }
            }
        }
    }

    fun updateReportTemplateField(field: ReportTemplateField) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                logger.log(TAG, "Updating report template field ${field.uid}, ${field.name}")
                try{
                    val result = reportTemplateRepository.updateReportTemplateField(field)
                    UpdateErrorState(!result.result, result.msg)
                    logger.log(
                        TAG,
                        "Result of updating report template field ${field.uid}, ${field.name} : ${result.result}"
                    )
                }
                catch(e : Exception){
                    UpdateErrorState(true, "Failed to update report template ${e.message}")
                    logger.logError(TAG, "Failed to update report template ${e.message}", e)
                }
            }
        }
    }

    fun UpdateErrorState(isError: Boolean, errorMsg: String) {
        this._isError.value = isError
        this._errorMsg.value = errorMsg

    }

}