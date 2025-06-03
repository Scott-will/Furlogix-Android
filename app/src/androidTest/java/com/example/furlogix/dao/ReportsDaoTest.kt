package com.furlogix.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.furlogix.dao.ReportEntryTestHelper
import com.furlogix.Database.AppDatabase
import com.furlogix.Database.DAO.ReportsDao
import com.furlogix.Database.Entities.Reports
import com.furlogix.R
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ReportsDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var reportDao: ReportsDao
    private lateinit var testHelper : ReportEntryTestHelper
    private val reportsCount : Int = 2
    private val reportsTemplateCount : Int = 2
    private val reportEntryPerTemplateCount = 5

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        context.deleteDatabase(R.string.database_name.toString())
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        val reportEntryDao = db.reportEntryDao()
        val reportTemplateDao = db.reportTemplateDao()
        reportDao = db.reportsDao()
        val petDao = db.petDao()
        val userDao = db.userDao()
        testHelper = ReportEntryTestHelper(userDao, petDao, reportDao, reportTemplateDao, reportEntryDao)
        testHelper.Seed(2, 2, reportsCount, reportsTemplateCount, reportEntryPerTemplateCount)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun RetrieveForPet() = runTest {
        val petReports = reportDao.getAllForPetFlow(1)
        Assert.assertEquals(2, petReports.size)
    }

    @Test
    fun Retrieveall() = runTest {
        val reports = reportDao.getAllReports()
        Assert.assertEquals(2*2*reportsCount, reports.size)
    }

    @Test
    fun Update() = runTest {
        var reports = reportDao.getByReportId(1)
        reports.name = "testupdate"
        reportDao.update(reports)
        reports = reportDao.getByReportId(1)
        Assert.assertEquals("testupdate", reports.name)

    }

    @Test
    fun updateReports() = runBlocking {
        val report = Reports(name = "Reports to Update", petId = 1)
        reportDao.insert(report)
        val insertedReports = reportDao.getAllReports().first()
        val updatedReports = insertedReports.copy(name = "Updated Name")
        reportDao.update(updatedReports)

        val result = reportDao.getByReportId(insertedReports.Id)
        assertEquals(result.name, "Updated Name")
    }

    @Test
    fun getAllForPetFlow() = runTest {
        val reports = reportDao.getAllForPetFlow(1)
        Assert.assertEquals(reports.size, reportsCount)
    }

    @Test
    fun deleteReports() = runBlocking {
        var reports = reportDao.getAllForPetFlow(1)
        reports.forEach { item ->
            reportDao.delete(item)
        }
        reports = reportDao.getAllForPetFlow(1)
        Assert.assertEquals(0, reports.size)
    }


}
