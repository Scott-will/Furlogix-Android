package com.furlogix.repositories

import androidx.lifecycle.LiveData
import com.furlogix.Database.DAO.UserDao
import com.furlogix.Database.Entities.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) : IUserRepository {
    override suspend fun getAll(): List<User> {
        return userDao.getAll()
    }

    override suspend fun insert(user: User): Long {
        return userDao.insert(user)
    }

    override suspend fun insertAll(vararg users: User) {
        userDao.insertAll(*users)
    }

    override suspend fun delete(user: User) {
        userDao.delete(user)
    }

    override fun getCurrentUserIdAsFlow(): Flow<Long> {
        return userDao.getCurrentUserIdAsFlow()
    }

    override suspend fun getCurrentUserId(): Long {
        return userDao.getCurrentUserId()
    }

    override suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    override suspend fun getUserByName(name: String): User? {
        return userDao.getUserByName(name)
    }

    override suspend fun countUsers(): Int {
        return userDao.countUsers()
    }

    override suspend fun deleteUserById(userId: Long) {
        userDao.deleteUserById(userId)
    }

    override fun getCurrentUserName(): LiveData<String> {
        return userDao.getCurrentUserName()
    }

    override fun getCurrentUserEmail(): LiveData<String>{
        return userDao.getCurrentUserEmail()
    }

    override suspend fun getCurrentUserEmailInLine(): String {
        return userDao.getCurrentUserEmailInLine()
    }

    override suspend fun updateUser(name: String, email: String) {
        userDao.updateUser(name, email)
    }

    override suspend fun setPendingReportsForUser(userId: Long) {
        userDao.setPendingReportsForUser(userId)
    }

    override suspend fun setNoPendingReportsForUser(userId: Long) {
        userDao.setNoPendingReportsForUser(userId)
    }
}