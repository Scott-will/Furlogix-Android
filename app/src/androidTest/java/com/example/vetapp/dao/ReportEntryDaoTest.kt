package com.example.vetapp.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.ReportEntryDao
import com.example.vetapp.R
import org.junit.After
import org.junit.Before
import org.junit.Test

class ReportEntryDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var reportEntryDao: ReportEntryDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        context.deleteDatabase(R.string.database_name.toString())
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        reportEntryDao = db.reportEntryDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun InsertAndRetrieveReportEntries(){

    }
}