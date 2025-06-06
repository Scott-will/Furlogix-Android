package com.furlogix.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furlogix.logger.ILogger
import com.furlogix.repositories.IReportEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReportEntryHistoryViewModel @Inject constructor(
    private val logger : ILogger,
    private val reportEntryRepository : IReportEntryRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val TAG = "Furlogix:" + ReportEntryHistoryViewModel::class.qualifiedName

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

    fun DeleteReportEntry(id : Int){
        viewModelScope.launch {
            withContext(ioDispatcher){
                try{
                    reportEntryRepository.deleteReportEntry(id)
                }
                catch(e : Exception){
                    UpdateErrorState(true, "Failed to delete entry ${e.message}")
                    logger.logError(TAG, "Failed to delete entry ${e.message}", e)
                }
            }
        }
    }
}