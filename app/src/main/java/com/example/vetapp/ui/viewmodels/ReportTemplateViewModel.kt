package com.example.vetapp.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import com.example.vetapp.reports.FormField

class ReportTemplateViewModel {
    private val _formFields = mutableStateListOf<FormField>()
    val formFields: List<FormField> = _formFields

    fun addFormField(field: FormField){
        _formFields.add(field)
    }

    fun removeFormField(field: FormField) {
        _formFields.remove(field)
    }
}