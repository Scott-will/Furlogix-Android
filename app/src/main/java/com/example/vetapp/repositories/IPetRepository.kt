package com.example.vetapp.repositories

import com.example.vetapp.Database.Entities.Pet
import kotlinx.coroutines.flow.Flow

interface IPetRepository {
    fun getAllPets(): Flow<List<Pet>>
    suspend fun getPetsForUser(userId: Long): List<Pet>
    suspend fun addPet(pet: Pet): Long
    suspend fun updatePet(pet: Pet)
    suspend fun deletePet(pet: Pet)
    suspend fun deleteAllPets()
}