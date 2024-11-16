package com.example.vetapp.repositories

import androidx.lifecycle.LiveData
import com.example.vetapp.Database.Entities.Reports

interface IReportsRepository {

    fun reportsObservable() : LiveData<List<Reports>>

    fun insertReport(report : Reports)

}