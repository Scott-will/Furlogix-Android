package com.furlogix.Database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.furlogix.Database.Entities.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface RemindersDao {
    @Insert
    fun insert(vararg reminder : Reminder)

    @Delete
    fun delete(vararg reminder : Reminder)

    @Query("Select * From Reminder")
    fun getAllRemindersFlow() : Flow<List<Reminder>>

    @Query("SELECT * FROM Reminder ORDER BY requestCode DESC LIMIT 1;")
    fun getLargestRequestCode() : Reminder?
}