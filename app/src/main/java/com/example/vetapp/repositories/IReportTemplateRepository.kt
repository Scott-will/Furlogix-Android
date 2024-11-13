package com.example.vetapp.repositories

import android.database.Observable
import androidx.lifecycle.LiveData
import com.example.vetapp.Database.Entities.ReportTemplateField

interface IReportTemplateRepository {
    fun ReportTemplateObservable() : LiveData<List<ReportTemplateField>>

    fun insertReportTemplateFields(reportTemplateFields : List<ReportTemplateField>)
}