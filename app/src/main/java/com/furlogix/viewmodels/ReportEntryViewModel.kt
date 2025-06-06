package com.furlogix.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furlogix.Database.DAO.UserDao
import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.logger.ILogger
import com.furlogix.repositories.IReportEntryRepository
import com.furlogix.repositories.IReportTemplateRepository
import com.furlogix.repositories.IReportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReportEntryViewModel@Inject constructor(private val logger : ILogger,
                                              private val reportEntryRepository : IReportEntryRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val TAG = "Furlogix:" + ReportEntryViewModel::class.qualifiedName

    //Error handling variables
    private var _isError = MutableStateFlow<Boolean>(false)
    private var _errorMsg = MutableStateFlow<String>("")
    var isError : StateFlow<Boolean> = _isError
    var errorMsg : StateFlow<String> = _errorMsg

    fun UpdateErrorState(isError: Boolean, errorMsg: String) {
        this._isError.value = isError
        if(this._isError.value){
            this._errorMsg.value = errorMsg
        }
        else{
            this._errorMsg.value = ""
        }

    }

    fun insertReportEntry(valueMap: Map<Int, MutableState<String>>, reportId: Int, timestamp : String) {
        var entries = mutableListOf<ReportEntry>()
        valueMap.forEach() { kvp ->
            val entry = ReportEntry(
                value = kvp.value.value,
                reportId = reportId,
                templateId = kvp.key,
                timestamp = timestamp
            )
            entries.add(entry)
        }
        viewModelScope.launch {
            withContext(ioDispatcher) {
                logger.log(TAG, "Inserting ${entries.count()} report entries")
                try{
                    var result = reportEntryRepository.insertEntries(entries)
                    logger.log(
                        TAG,
                        "Result of inserting ${entries.count()} report entries : ${result.result}"
                    )
                    UpdateErrorState(!result.result, result.msg)
                }
                catch (e : Exception){
                    logger.logError(TAG, "Failed to insert report entries ${e.message}", e)
                    UpdateErrorState(true, "Failed to insert report entries ${e.message}")
                }

            }
        }
    }

}