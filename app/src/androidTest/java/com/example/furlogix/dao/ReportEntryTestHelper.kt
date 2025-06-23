package com.example.furlogix.dao

import com.furlogix.Database.DAO.PetDao
import com.furlogix.Database.DAO.ReportEntryDao
import com.furlogix.Database.DAO.ReportTemplateDao
import com.furlogix.Database.DAO.ReportsDao
import com.furlogix.Database.DAO.UserDao
import com.furlogix.Database.Entities.Pet
import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.Database.Entities.Reports
import com.furlogix.Database.Entities.User
import com.furlogix.reports.FieldType
import kotlinx.coroutines.runBlocking

class ReportEntryTestHelper(val userDao: UserDao,
                            val petDao: PetDao,
                            val reportsDao: ReportsDao,
                            val reportTemplateDao: ReportTemplateDao,
                            val reportEntryDao: ReportEntryDao) {
    fun CreateReport(name : String, petId : Int) : Int{
        val report = Reports(name = name, petId = petId)
        reportsDao.insert(report)
        val reportId = reportsDao.getAllReports().last().Id
        return reportId
    }

    fun CreatePet(name: String, userId : Long) : Int = runBlocking{
        val pet = Pet(name = name,
            type = "cat",
            userId = userId,
            description = ""
            )
        petDao.insert(pet)
        val petId = petDao.getPetsForUser(userId).last().uid
        return@runBlocking petId
    }

    fun CreateUser(name : String) : Int{
        val user = User(name = name, surname = "tester",
            email = "tester@test.com")
        userDao.insert(user)
        val userId = userDao.getUserByName(name)?.uid
        return userId!!
    }

    fun CreateReportTemplate(name : String, reportId : Int) : Int{
        val reportTemplate = ReportTemplateField(name = name,
            reportId = reportId,
            fieldType = FieldType.TEXT,
            icon = "",
            units = "")
        reportTemplateDao.insert(reportTemplate)
        return reportTemplateDao.getReportById(reportId).last().uid
    }

    fun CreateReportEntry(value : String, reportId : Int, templateId : Int, timestamp : String){
        val reportEntry = ReportEntry(value = value,
            reportId = reportId,
            templateId = templateId,
            timestamp = timestamp)
        reportEntryDao.insert(reportEntry)
    }

    fun Seed(numUsers : Int,
             numPetsForUsers: Int,
             numReportsForPet : Int,
             numTemplatesForReport : Int,
             numEntriesForTemplate : Int){
        for(i in 1.. numUsers){
            val userId = this.CreateUser("tester${i}")
            for(j in 1..numPetsForUsers){
                val petId = this.CreatePet("testerPet${j}", userId.toLong())
                for(k in 1.. numReportsForPet){
                    val reportId = this.CreateReport("testPet${j}Report${k}", petId)
                    for(m in 1..numTemplatesForReport){
                        val templateId = this.CreateReportTemplate("testPet${j}Report${k}Template${m}", reportId)
                        for(n in 1..numEntriesForTemplate){
                            this.CreateReportEntry("testPet${j}Report${k}Template${m}Entry${n}",
                                reportId, templateId, timestamp = "now")
                        }
                    }
                }

            }
        }

    }
}