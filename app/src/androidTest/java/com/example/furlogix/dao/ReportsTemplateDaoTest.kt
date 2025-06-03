package com.furlogix.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.furlogix.dao.ReportEntryTestHelper
import com.furlogix.Database.AppDatabase
import com.furlogix.Database.DAO.ReportTemplateDao
import com.furlogix.R
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReportsTemplateDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var reportTemplateDao: ReportTemplateDao
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
        reportTemplateDao = db.reportTemplateDao()
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
    fun updateTemplate() = runBlocking {
        val insertedTemplate = reportTemplateDao.getTemplateById(1)
        val updatedTemplate = insertedTemplate.copy(name = "Updated Name")
        reportTemplateDao.update(updatedTemplate)
        val result = reportTemplateDao.getTemplateById(1)
        assertEquals(result.name, "Updated Name")
    }

    @Test
    fun GetAllReportTemplateField() = runBlocking {
        val reportTemplates = reportTemplateDao.getAll()
        Assert.assertEquals(2*2*reportsCount*reportsTemplateCount, reportTemplates.size)
    }

    @Test
    fun GetAllReportTemplateFieldForReport() = runBlocking {
        val reportTemplates = reportTemplateDao.getReportById(1)
        Assert.assertEquals(reportsTemplateCount, reportTemplates.size)
    }

    @Test
    fun deleteTemplate() = runBlocking {
        val toDelete = reportTemplateDao.getTemplateById(1)
        reportTemplateDao.delete(toDelete)
        val result = reportTemplateDao.getTemplateById(1)
        assert(result == null)
    }

}