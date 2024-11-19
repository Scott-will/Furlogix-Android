package com.example.vetapp.Database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vetapp.Database.Entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM user_table WHERE uid = :userId")
    fun getUserById(userId: Int): User?

    @Query("SELECT * FROM user_table WHERE name = :name LIMIT 1")
    fun getUserByName(name: String): User?

    @Query("SELECT COUNT(*) FROM user_table")
    suspend fun countUsers(): Int

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()
}