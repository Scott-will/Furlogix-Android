package com.furlogix.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furlogix.Database.DAO.UserDao
import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.repositories.IReportEntryRepository
import com.furlogix.repositories.IReportTemplateRepository
import com.furlogix.repositories.IReportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReportEntryViewModel@Inject constructor(
    private val reportTemplateRepository : IReportTemplateRepository,
    private val reportRepository : IReportsRepository,
    private val userDao: UserDao,
    private val reportEntryRepository : IReportEntryRepository
) : ViewModel() {

    private val TAG = "Furlogix:" + ReportEntryViewModel::class.qualifiedName

    //Error handling variables
    private var _isError = MutableStateFlow<Boolean>(false)
    private var _errorMsg = MutableStateFlow<String>("")
    var isError : StateFlow<Boolean> = _isError
    var errorMsg : StateFlow<String> = _errorMsg

    fun UpdateErrorState(isError: Boolean, errorMsg: String) {
        this._isError.value = isError
        this._errorMsg.value = errorMsg

    }

    fun insertReportEntry(valueMap: Map<Int, MutableState<String>>, reportId: Int, timestamp : String) {
        var entries = mutableListOf<ReportEntry>()
        val time = Date()
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

}