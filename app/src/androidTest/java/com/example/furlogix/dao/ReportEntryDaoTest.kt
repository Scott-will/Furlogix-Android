package com.furlogix.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.furlogix.dao.ReportEntryTestHelper
import com.furlogix.Database.AppDatabase
import com.furlogix.Database.DAO.ReportEntryDao
import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.R
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ReportEntryDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var reportEntryDao: ReportEntryDao
    private lateinit var testHelper : ReportEntryTestHelper
    private val reportsCount : Int = 2
    private val reportsTemplateCount : Int = 2
    private val reportEntryPerTemplateCount = 5

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        context.deleteDatabase(R.string.database_name.toString())
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        reportEntryDao = db.reportEntryDao()
        val reportTemplateDao = db.reportTemplateDao()
        val reportDao = db.reportsDao()
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
    fun TestGetForReports() = runTest {
            val reportList: List<ReportEntry> = reportEntryDao.getAllReportEntriesById(1)
            Assert.assertEquals(reportsTemplateCount*reportEntryPerTemplateCount, reportList.size)
    }

    @Test
    fun TestGetForReportTemplates() = runTest {
            val reportList: List<ReportEntry> = reportEntryDao.getAllReportEntriesForTemplate(1).toList()
            Assert.assertEquals(reportEntryPerTemplateCount, reportList.size)
    }

    @Test
    fun TestGetAll() = runTest {
            val reportList: List<ReportEntry> = reportEntryDao.getAllReportEntries()
            Assert.assertEquals(reportEntryPerTemplateCount*reportsTemplateCount*reportsCount*2*2, reportList.size)
    }

    @Test
    fun TestMarkAsSent_AndDelete() = runTest {
        var reportList: List<ReportEntry> = reportEntryDao.getAllReportEntriesById(1)
        reportList.forEach { item ->
            item.sent = true
            reportEntryDao.updateReportEntries(item)
        }
        val reportListUpdate: List<ReportEntry> = reportEntryDao.getAllReportEntriesById(1)

        Assert.assertEquals(reportsTemplateCount*reportEntryPerTemplateCount, reportListUpdate.size)
        reportListUpdate.forEach{item ->
            Assert.assertTrue(item.sent)
        }
        reportEntryDao.deleteSentReportEntries()
        reportList = reportEntryDao.getAllReportEntriesById(1)
        Assert.assertTrue(reportList.isEmpty())
    }
}