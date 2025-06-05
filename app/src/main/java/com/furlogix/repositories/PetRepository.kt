package com.furlogix.repositories

import com.furlogix.Database.DAO.PetDao
import com.furlogix.Database.Entities.Pet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PetRepository @Inject constructor(
    private val petDao: PetDao
) : IPetRepository {

    override fun getAllPets(): Flow<List<Pet>> {
        return petDao.getAll()
    }

    override suspend fun getPetById(id : Int) : Pet{
        return petDao.getPetById(id)
    }

    override suspend fun getPetsForUser(userId: Long): List<Pet> {
        return petDao.getPetsForUser(userId)
    }

    override suspend fun addPet(pet: Pet): Long {
        return petDao.insert(pet)
    }

    override suspend fun updatePet(pet: Pet) {
        petDao.update(pet)
    }

    override suspend fun deletePet(pet: Pet) {
        petDao.delete(pet)
    }

    override suspend fun deleteAllPets() {
        petDao.deleteAll()
    }
}