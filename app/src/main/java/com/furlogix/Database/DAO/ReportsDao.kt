package com.furlogix.Database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.furlogix.Database.Entities.Reports
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportsDao {
    @Query("SELECT * FROM Reports Where petId = :petId")
    fun getAllForPetFlow(petId : Int): List<Reports>

    @Query("SELECT * FROM Reports")
    fun getAllFlow(): Flow<List<Reports>>

    @Query("SELECT * FROM Reports")
    fun getAllReports() : List<Reports>

    @Query("SELECT * FROM Reports Where Id = :id Limit 1")
    fun getByReportIdFlow(id : Int): Flow<Reports>

    @Query("SELECT * FROM Reports Where Id = :id Limit 1")
    fun getByReportId(id : Int): Reports

    @Insert
    fun insert(vararg reports: Reports)

    @Update
    fun update(vararg report : Reports)

    @Delete
    fun delete(vararg report : Reports)
}