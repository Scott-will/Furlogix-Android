package com.example.vetapp.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.UserDao
import com.example.vetapp.Database.Entities.User
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userDao = db.userDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndRetrieveUser() = runBlocking {
        val user = User(name = "Test", surname = "User", email = "test@example.com")
        userDao.insert(user)
        val users = userDao.getAll()
        assertEquals(users.size, 1)
        assertEquals(users[0].name, "Test")
    }

    @Test
    fun updateUser() = runBlocking {
        val user = User(name = "Original Name", surname = "User", email = "email@example.com")
        userDao.insert(user)
        val insertedUser = userDao.getAll().first()
        val updatedUser = insertedUser.copy(name = "Updated Name")
        userDao.update(updatedUser)

        val result = userDao.getUserById(insertedUser.uid)
        assertEquals(result?.name, "Updated Name")
    }

    @Test
    fun deleteUser() = runBlocking {
        val user = User(name = "User to Delete", surname = "User", email = "delete@example.com")
        userDao.insert(user)
        val insertedUser = userDao.getAll().first()
        userDao.delete(insertedUser)

        val result = userDao.getUserById(insertedUser.uid)
        assertNull(result)
    }
}