package com.example.vetapp.repositories

import com.example.vetapp.Database.Entities.ReportEntry

interface IReportEntryRepository {

    fun insertEntries(entries : List<ReportEntry> )
}