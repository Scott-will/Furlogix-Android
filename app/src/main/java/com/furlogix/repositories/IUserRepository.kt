package com.furlogix.repositories

import androidx.lifecycle.LiveData
import com.furlogix.Database.Entities.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun getAll() : List<User>

    suspend fun insert(user : User) : Long

    suspend fun insertAll(vararg users: User)

    suspend fun delete(user : User)

    fun getCurrentUserIdAsFlow(): Flow<Long>

    suspend fun getCurrentUserId():Long

    suspend fun getUserById(userId: Int): User?

    suspend fun getUserByName(name : String): User?

    suspend fun countUsers(): Int

    suspend fun deleteUserById(userId : Long)

    fun getCurrentUserName() : LiveData<String>

    suspend fun getCurrentUserEmailInLine(): String

    fun getCurrentUserEmail(): LiveData<String>

    suspend fun updateUser(name: String, email: String)

    suspend fun setPendingReportsForUser(userId: Long)

    suspend fun setNoPendingReportsForUser(userId : Long)
}