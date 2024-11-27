package com.example.vetapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.ReportsDao
import com.example.vetapp.Database.Entities.Reports
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ReportsDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var reportDao: ReportsDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        context.deleteDatabase(R.string.database_name.toString())
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        reportDao = db.reportsDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndRetrieveReports() = runBlocking {
        val report = Reports(name = "Test")
        reportDao.insert(report)
        val reports = reportDao.getAll().first()
        assertEquals(reports.count(), 1)
        assertEquals(reports.first().name, "Test")
    }

    @Test
    fun updateReports() = runBlocking {
        val report = Reports(name = "Reports to Update")
        reportDao.insert(report)
        val insertedReports = reportDao.getAll().first().first()
        val updatedReports = insertedReports.copy(name = "Updated Name")
        reportDao.update(updatedReports)

        val result = reportDao.getByReportId(insertedReports.Id)
        assertEquals(result.first().name, "Updated Name")
    }

    @Test
    fun deleteReports() = runBlocking {
        val report = Reports(name = "Reports to Delete")
        reportDao.insert(report)
        val insertedReports = reportDao.getAll().first().first()
        assertNotNull(insertedReports)
        reportDao.delete(insertedReports)

        val result = reportDao.getByReportId(1).first()
        assertNull(result)
    }
}
