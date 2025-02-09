package com.example.vetapp.Database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vetapp.Database.Entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT uid FROM user_table LIMIT 1")
    fun getCurrentUserIdAsFlow(): Flow<Long>

    @Query("SELECT uid FROM user_table LIMIT 1")
    fun getCurrentUserId(): Long

    @Query("SELECT * FROM user_table WHERE uid = :userId")
    fun getUserById(userId: Int): User?

    @Query("SELECT * FROM user_table WHERE name = :name LIMIT 1")
    fun getUserByName(name: String): User?

    @Query("SELECT COUNT(*) FROM user_table")
    suspend fun countUsers(): Int

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM user_table WHERE uid = :userId")
    suspend fun deleteUserById(userId: Long)

    @Query("SELECT name FROM user_table LIMIT 1")
    fun getCurrentUserName(): LiveData<String>

    @Query("SELECT email FROM user_table LIMIT 1")
    fun getCurrentUserEmail(): LiveData<String>

    @Query("UPDATE user_table SET name = :name, email = :email")
    suspend fun updateUser(name: String, email: String)

    @Query("UPDATE user_table Set pendingSentReports = true WHERE uid = :userId")
    suspend fun setPendingReportsForUser(userId: Long)

    @Query("UPDATE user_table Set pendingSentReports = false WHERE uid = :userId")
    suspend fun setNoPendingReportsForUser(userId: Long)
}