package com.example.vetapp.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReportsTemplateDaoTest {
    /*
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
        userDao.insert(User(name = "abcd", surname = "abcd", email = "abcd", petName = "abcd"))
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

     */
}