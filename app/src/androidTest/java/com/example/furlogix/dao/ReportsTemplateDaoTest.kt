package com.furlogix.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.furlogix.Database.AppDatabase
import com.furlogix.Database.DAO.ReportTemplateDao
import com.furlogix.Database.DAO.ReportsDao
import com.furlogix.Database.Entities.Pet
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.Database.Entities.Reports
import com.furlogix.Database.Entities.User
import com.furlogix.R
import com.furlogix.reports.FieldType
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
        val petDao = db.petDao()
        userDao.insert(User(name = "abcd", surname = "abcd", email = "abcd"))
        petDao.insert(Pet(name = "test", type = "cat", description = "", userId = 1))
        reportDao.insert(Reports(name = "Test", petId = 1))
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
        val template = ReportTemplateField(name = "Test", fieldType = FieldType.CHECKBOX, reportId = 1)
        reportTemplateDao.insert(template)
        val insertedUser = reportTemplateDao.getAll().first()
        reportTemplateDao.delete(insertedUser)

        val result = reportTemplateDao.getReportById(1)
        assert(result.isEmpty())
    }

}