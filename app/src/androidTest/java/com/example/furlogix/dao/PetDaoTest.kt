package com.furlogix.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.furlogix.Database.AppDatabase
import com.furlogix.Database.DAO.PetDao
import com.furlogix.Database.DAO.UserDao
import com.furlogix.Database.Entities.Pet
import com.furlogix.Database.Entities.User
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class PetDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var petDao: PetDao
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        database.query("PRAGMA foreign_keys=ON", null)

        petDao = database.petDao()
        userDao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertPetAndRetrieveIt() = runTest {
        val user = User(name = "John", surname = "Doe", email = "john@example.com")
        val userId = userDao.insert(user).toLong()

        val pet = Pet(name = "Buddy", type = "Dog", description = "Golden Retriever", userId = userId)
        petDao.insert(pet)

        val retrievedPets = petDao.getAll().first()
        assertEquals(1, retrievedPets.size)
        assertEquals("Buddy", retrievedPets[0].name)
    }

    @Test
    fun insertMultiplePetsAndRetrieve() = runTest {
        val user = User(name = "John", surname = "Doe", email = "john@example.com")
        val userId = userDao.insert(user).toLong()

        val pet1 = Pet(name = "Buddy", type = "Dog", description = "Golden Retriever", userId = userId)
        val pet2 = Pet(name = "Max", type = "Cat", description = "Siamese", userId = userId)
        petDao.insertAll(pet1, pet2)

        val retrievedPets = petDao.getAll().first()
        assertEquals(2, retrievedPets.size)
        assertEquals("Buddy", retrievedPets[0].name)
        assertEquals("Max", retrievedPets[1].name)
    }

    @Test
    fun updatePet() = runTest {
        val user = User(name = "John", surname = "Doe", email = "john@example.com")
        val userId = userDao.insert(user).toLong()

        val pet = Pet(name = "Buddy", type = "Dog", description = "Golden Retriever", userId = userId)
        petDao.insert(pet)

        val updatedPet = Pet(uid = 1, name = "Buddy Updated", type = "Dog", description = "Labrador", userId = userId)
        petDao.update(updatedPet)

        val retrievedPets = petDao.getAll().first()
        assertEquals(1, retrievedPets.size)
        assertEquals("Buddy Updated", retrievedPets[0].name)
    }

    @Test
    fun deletePet() = runTest {
        val user = User(name = "John", surname = "Doe", email = "john@example.com")
        val userId = userDao.insert(user).toLong()

        val pet = Pet(name = "Buddy", type = "Dog", description = "Golden Retriever", userId = userId)
        val petId = petDao.insert(pet)
        val petWithId = pet.copy(uid = petId.toInt())
        petDao.delete(petWithId)

        val retrievedPets = petDao.getAll().first()
        assertEquals(0, retrievedPets.size)
    }

    @Test
    fun deleteAllPets() = runTest {
        val user = User(name = "John", surname = "Doe", email = "john@example.com")
        val userId = userDao.insert(user).toLong()

        val pet1 = Pet(name = "Buddy", type = "Dog", description = "Golden Retriever", userId = userId)
        val pet2 = Pet(name = "Max", type = "Cat", description = "Siamese", userId = userId)
        petDao.insertAll(pet1, pet2)

        petDao.deleteAll()

        val retrievedPets = petDao.getAll().first()
        assertEquals(0, retrievedPets.size)
    }

    @Test
    fun getPetsForUser() = runTest {
        val user1 = User(name = "John", surname = "Doe", email = "john@example.com")
        val user2 = User(name = "Jane", surname = "Smith", email = "jane@example.com")
        val user1Id = userDao.insert(user1).toLong()
        val user2Id = userDao.insert(user2).toLong()

        val pet1 = Pet(name = "Buddy", type = "Dog", description = "Golden Retriever", userId = user1Id)
        val pet2 = Pet(name = "Max", type = "Cat", description = "Siamese", userId = user2Id)
        val pet3 = Pet(name = "Bella", type = "Dog", description = "Labrador", userId = user1Id)

        petDao.insertAll(pet1, pet2, pet3)

        val user1Pets = petDao.getPetsForUser(user1Id)
        assertEquals(2, user1Pets.size)
        assertEquals(listOf(pet1.copy(uid = 1), pet3.copy(uid = 3)), user1Pets)
    }

    @Test
    fun foreignKeyCascadeDeletesPets() = runTest {
        val user = User(name = "John", surname = "Doe", email = "john@example.com")
        val userId = userDao.insert(user).toLong()

        val pet1 = Pet(name = "Buddy", type = "Dog", description = "Golden Retriever", userId = userId)
        val pet2 = Pet(name = "Max", type = "Cat", description = "Siamese", userId = userId)
        petDao.insertAll(pet1, pet2)

        val petsBeforeDelete = petDao.getAll().first()
        assertEquals(2, petsBeforeDelete.size)

        val userToDelete = user.copy(uid = userId.toInt())
        userDao.delete(userToDelete)

        val petsAfterUserDelete = petDao.getAll().first()
        assertEquals(0, petsAfterUserDelete.size)
    }
}