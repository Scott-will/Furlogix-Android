package com.furlogix.Database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.furlogix.Database.Entities.Pet
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Query("SELECT * FROM pet_table")
    fun getAll(): Flow<List<Pet>>

    @Insert
    fun insert(pet: Pet): Long

    @Insert
    fun insertAll(vararg pets: Pet)

    @Update
    fun update(pet: Pet)

    @Delete
    fun delete(pet: Pet)

    @Query("DELETE FROM pet_table")
    fun deleteAll()

    @Query("SELECT * FROM pet_table WHERE userId = :userId")
    suspend fun getPetsForUser(userId: Long): List<Pet>

}