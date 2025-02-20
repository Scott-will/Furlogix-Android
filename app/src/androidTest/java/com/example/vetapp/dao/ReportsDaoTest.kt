package com.example.vetapp.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.ReportsDao
import com.example.vetapp.Database.Entities.Pet
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.Database.Entities.User
import com.example.vetapp.R
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
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
        val userDao = db.userDao()
        val petDao = db.petDao()
        userDao.insert(User(name = "abcd", surname = "abcd", email = "abcd"))
        petDao.insert(Pet(name = "test", type = "cat", description = "", userId = 1))
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndRetrieveReports() = runBlocking {
        val report = Reports(name = "Test", petId = 1)
        reportDao.insert(report)
        val reports = reportDao.getAllReports()
        assertEquals(reports.count(), 1)
        assertEquals(reports.first().name, "Test")
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
    fun deleteReports() = runBlocking {
        val report = Reports(name = "Reports to Delete", petId = 1)
        reportDao.insert(report)
        val insertedReports = reportDao.getAllReports().first()
        assertNotNull(insertedReports)
        reportDao.delete(insertedReports)

        val result = reportDao.getByReportId(1)
        assertNull(result)
    }
}
