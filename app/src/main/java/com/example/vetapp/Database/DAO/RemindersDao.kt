package com.example.vetapp.Database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vetapp.Database.Entities.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface RemindersDao {
    @Insert
    fun insert(vararg reminder : Reminder)

    @Query("Select * From Reminder")
    fun getAllRemindersFlow() : Flow<Reminder>
}