package com.furlogix.repositories

import com.furlogix.Database.Entities.Pet
import kotlinx.coroutines.flow.Flow

interface IPetRepository {
    fun getAllPets(): Flow<List<Pet>>
    suspend fun getPetsForUser(userId: Long): List<Pet>
    suspend fun addPet(pet: Pet): Long
    suspend fun updatePet(pet: Pet)
    suspend fun deletePet(pet: Pet)
    suspend fun deleteAllPets()
    suspend fun getPetById(petId: Int): Pet?
}