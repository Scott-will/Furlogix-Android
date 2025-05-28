package com.furlogix.viewmodels

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
import javax.inject.Inject

@HiltViewModel
class ReportEntryHistoryViewModel @Inject constructor(
    private val reportTemplateRepository : IReportTemplateRepository,
    private val reportRepository : IReportsRepository,
    private val userDao: UserDao,
    private val reportEntryRepository : IReportEntryRepository
) : ViewModel() {

    private val TAG = "Furlogix:" + ReportEntryHistoryViewModel::class.qualifiedName

    //Error handling variables
    private var _isError = MutableStateFlow<Boolean>(false)
    private var _errorMsg = MutableStateFlow<String>("")
    var isError : StateFlow<Boolean> = _isError
    var errorMsg : StateFlow<String> = _errorMsg
    fun UpdateErrorState(isError: Boolean, errorMsg: String) {
        this._isError.value = isError
        this._errorMsg.value = errorMsg

    }

    fun DeleteReportEntry(id : Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                reportEntryRepository.deleteReportEntry(id)
            }
        }
    }

    fun EditReportEntry(entry : ReportEntry){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                reportEntryRepository.updateReportEntries(listOf(entry))
            }
        }
    }
}