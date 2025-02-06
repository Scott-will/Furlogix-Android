package com.example.vetapp.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.ReportTemplateDao
import com.example.vetapp.Database.DAO.ReportsDao
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.Database.Entities.User
import com.example.vetapp.R
import com.example.vetapp.reports.FieldType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReportsTemplateDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var reportTemplateDao: ReportTemplateDao
    private lateinit var reportDao: ReportsDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        context.deleteDatabase(R.string.database_name.toString())
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        reportTemplateDao = db.reportTemplateDao()
        reportDao = db.reportsDao()
        val userDao = db.userDao()
        userDao.insert(User(name = "abcd", surname = "abcd", email = "abcd"))
        reportDao.insert(Reports(name = "Test", userId = 1))
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndRetrieveTemplate() = runBlocking {
        val reportTemplateField = ReportTemplateField(name = "Test", fieldType = FieldType.NUMBER, reportId = 1)
        reportTemplateDao.insert(reportTemplateField)
        val templates = reportTemplateDao.getAll()
        assertEquals(templates.count(), 1)
        assertEquals(templates.firstOrNull()?.name, "Test")
    }

    @Test
    fun updateTemplate() = runBlocking {
        val reportTemplateField = ReportTemplateField(name = "Original Name", fieldType = FieldType.TEXT, reportId = 1)
        reportTemplateDao.insert(reportTemplateField)
        val insertedTemplate = reportTemplateDao.getAll().firstOrNull()
        val updatedTemplate = insertedTemplate?.copy(name = "Updated Name")
        reportTemplateDao.update(updatedTemplate!!)

        val result = reportTemplateDao.getAll().firstOrNull { x -> x.uid == insertedTemplate.uid }
        assertEquals(result?.name, "Updated Name")
    }

    @Test
    fun deleteTemplate() = runBlocking {
        val template = ReportTemplateField(name = "Test", fieldType = FieldType.BOOLEAN, reportId = 1)
        reportTemplateDao.insert(template)
        val insertedUser = reportTemplateDao.getAll().first()
        reportTemplateDao.delete(insertedUser)

        val result = reportTemplateDao.getReportById(1)
        assert(result.isEmpty())
    }

}