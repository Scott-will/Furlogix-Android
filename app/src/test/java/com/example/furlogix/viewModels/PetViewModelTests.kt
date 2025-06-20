package com.example.furlogix.viewModels

import com.furlogix.Database.Entities.Pet
import com.furlogix.logger.ILogger
import com.furlogix.repositories.IPetRepository
import com.furlogix.viewmodels.PetViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.unmockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PetViewModelTests {

    private lateinit var petRepository: IPetRepository
    private lateinit var viewModel : PetViewModel
    private lateinit var logger : ILogger
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup(){
        Dispatchers.setMain(this.testDispatcher)

        petRepository = mockk()
        logger = mockk()
        every { logger.log(any(), any()) } just Runs
        every { logger.logError(any(), any(), any()) } just Runs

        viewModel = PetViewModel(logger, petRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        unmockkObject(logger)
        unmockkObject(petRepository)
    }

    @Test
    fun petViewModel_ValidateForm_EmptyName_Fails(){
        viewModel.name.value = ""
        Assert.assertEquals(false, viewModel.validateForm())
        Assert.assertNotEquals(viewModel.nameError, "")
        Assert.assertNotEquals(null, viewModel.nameError)
    }

    @Test
    fun petViewModel_ValidateForm_ValidName_Success(){
        viewModel.name.value = "tester"
        Assert.assertEquals(true, viewModel.validateForm())
        Assert.assertEquals(null, viewModel.nameError.value)

    }

    @Test
    fun petViewModel_ClearFields_Success(){
        viewModel.name.value = "tester"
        viewModel.type.value = "tester"
        viewModel.description.value = "tester"
        viewModel.clearFields()
        Assert.assertNotEquals("tester", viewModel.name.value)
        Assert.assertNotEquals("tester", viewModel.type.value)
        Assert.assertNotEquals("tester", viewModel.description.value)
    }

    @Test
    fun petViewModel_LoadPetsForUsers_Success() = runTest{
        val pet1 = Pet(1, "tester", "cat", "", 1)
        val pet2 = Pet(2, "tester2", "dog", "", 1)
        val pet3 = Pet(3, "tester3", "bird", "", 1)
        val pets = listOf(pet1, pet2, pet3)
        coEvery {petRepository.getPetsForUser(1)} returns pets
        viewModel.loadPetsForUser(1)
        advanceUntilIdle()
        Assert.assertEquals(pets, viewModel.pets.value)
    }

    @Test
    fun petViewModel_LoadPetsForUsers_Fails() = runTest{
        coEvery { petRepository.getPetsForUser(1) } throws RuntimeException("Failed to fetch pets")
        viewModel.loadPetsForUser(1)
        advanceUntilIdle()
        Assert.assertEquals(emptyList<Pet>(), viewModel.pets.value)
    }

    @Test
    fun petViewModel_DeletePet_Success() =  runTest{
        val pet1 = Pet(1, "tester", "cat", "", 1)
        val pet2 = Pet(2, "tester2", "dog", "", 1)
        val pet3 = Pet(3, "tester3", "bird", "", 1)
        var pets = listOf(pet1, pet2, pet3)
        coEvery {petRepository.getPetsForUser(1)} returns pets
        viewModel.loadPetsForUser(1)
        advanceUntilIdle()
        Assert.assertEquals(pets, viewModel.pets.value)
        coEvery { petRepository.deletePet(pet1) } just runs
        pets = listOf(pet2, pet3)
        coEvery {petRepository.getPetsForUser(1)} returns pets
        viewModel.deletePet(pet1)
        advanceUntilIdle()
        Assert.assertEquals(pets, viewModel.pets.value)
    }

    @Test
    fun petViewModel_PopulateCurrentPet_Success() = runTest{
        val pet1 = Pet(1, "tester", "cat", "", 1)
        coEvery {petRepository.getPetById(1)} returns pet1
        viewModel.populateCurrentPet(1)
        advanceUntilIdle()
        Assert.assertEquals(pet1, viewModel.currentPet.value)
    }

    @Test
    fun petViewModel_PopulateCurrentPet_Fails() = runTest{
        val pet1 = Pet(1, "tester", "cat", "", 1)
        coEvery {petRepository.getPetById(1)} throws RuntimeException("Failed to fetch current pet")
        viewModel.populateCurrentPet(1)
        advanceUntilIdle()
        Assert.assertEquals(null, viewModel.currentPet.value)
    }

}